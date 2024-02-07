CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE "Restaurant" (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    crtime timestamp without time zone NOT NULL DEFAULT now(),
    name varchar(256) NOT NULL,
    phone varchar(16) NOT NULL,
    address varchar(256) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE "User" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    email CITEXT NOT NULL,
    password char(64) NOT NULL,
    firstname varchar(256) NOT NULL,
    lastname varchar(256) NOT NULL,
    type varchar(16) NOT NULL,
    username varchar(256),
    phone varchar(16),
    ssn varchar(256),
    fk_restaurant uuid,
    FOREIGN KEY (fk_restaurant) REFERENCES "Restaurant" (id),
    unique(email),
    unique(ssn),
    unique(username)
);

CREATE TABLE "Dish" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    type varchar(16) NOT NULL,
    name CITEXT NOT NULL,
    description varchar(4096) NOT NULL,
    fk_restaurant uuid NOT NULL,
    active boolean NOT NULL DEFAULT true,
    FOREIGN KEY (fk_restaurant) REFERENCES "Restaurant" (id),
    unique(name, fk_restaurant)
);

CREATE TABLE "Ingredient" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    name CITEXT NOT NULL,
    fk_parent uuid,
    FOREIGN KEY (fk_parent) REFERENCES "Ingredient" (id),
    unique(name)
);

CREATE TABLE "Dish_Ingredient" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    fk_ingredient UUID NOT NULL,
    fk_dish UUID NOT NULL,
    cooked BOOLEAN NOT NULL DEFAULT false,
    optional BOOLEAN NOT NULL DEFAULT false,
    unique(fk_dish, fk_ingredient),
    FOREIGN KEY(fk_ingredient) REFERENCES "Ingredient"(id),
    FOREIGN KEY(fk_dish) REFERENCES "Dish"(id)
);

CREATE TABLE "ExcludedGroup" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    name CITEXT NOT NULL,
    unique(name)
);

CREATE TABLE "ExcludedGroup_Ingredient" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    fk_ingredient UUID NOT NULL,
    fk_excluded_group UUID NOT NULL,
    cooked BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY(fk_ingredient) REFERENCES "Ingredient"(id),
    FOREIGN KEY(fk_excluded_group) REFERENCES "ExcludedGroup"(id),
    unique(fk_ingredient, fk_excluded_group)
);

CREATE TABLE "User_ExcludedIngredient" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    fk_user UUID NOT NULL,
    fk_ingredient UUID NOT NULL,
    cooked BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY(fk_user) REFERENCES "User"(id),
    FOREIGN KEY(fk_ingredient) REFERENCES "Ingredient"(id),
    unique(fk_ingredient, fk_user)
);

CREATE TABLE "User_ExcludedGroup" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    fk_user UUID NOT NULL,
    fk_excluded_group UUID NOT NULL,
    FOREIGN KEY(fk_user) REFERENCES "User"(id),
    FOREIGN KEY(fk_excluded_group) REFERENCES "ExcludedGroup"(id),
    unique(fk_excluded_group, fk_user)
);

CREATE TABLE "Allergy" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    name CITEXT NOT NULL,
    unique(name)
);

CREATE TABLE "User_Allergy" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    fk_user UUID NOT NULL,
    fk_allergy UUID NOT NULL,
    FOREIGN KEY(fk_user) REFERENCES "User"(id),
    FOREIGN KEY(fk_allergy) REFERENCES "Allergy"(id),
    unique(fk_user, fk_allergy)
);


CREATE OR REPLACE PROCEDURE restinfo(
	IN _id varchar,
	OUT _name varchar,
	OUT _phone varchar,
	OUT _address varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT name, phone, address
		INTO _name, _phone, _address
		FROM "Restaurant" WHERE id = _id::uuid;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE userinfo(
	IN _email varchar,
	OUT _id varchar,
	OUT _type varchar,
	OUT _firstname varchar,
	OUT _lastname varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT id::varchar, type, firstname, lastname
		INTO _id, _type, _firstname, _lastname
		FROM "User" WHERE email = _email::CITEXT;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE userinfo_pickie(
	IN _email varchar,
	OUT _username varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT username INTO _username FROM "User" WHERE email = _email::CITEXT;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE userinfo_rest(
	IN _email varchar,
	OUT _phone varchar,
	OUT _ssn varchar,
	OUT _restaurant varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT phone, ssn, fk_restaurant INTO _phone, _ssn, _restaurant FROM "User" WHERE email = _email::CITEXT;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE login(IN _email varchar, IN _password varchar, OUT _check boolean)
LANGUAGE plpgsql
AS $$
DECLARE
	userid UUID;
BEGIN
    SELECT id INTO userid FROM "User" WHERE email = _email::CITEXT AND password = _password;
    IF(userid IS NULL) THEN
		_check := false;
		RETURN;
	END IF;
	_check := true;
END;
$$;

CREATE OR REPLACE PROCEDURE add_restaurant(
    IN _name varchar(256),
    IN _phone varchar(16),
    IN _address varchar(256),
    OUT _id varchar(256))
LANGUAGE plpgsql
AS $BODY$
BEGIN
	INSERT INTO "Restaurant" (name, phone, address)
	    VALUES (_name, _phone, _address) RETURNING id::varchar INTO _id;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_admin(
    IN _email varchar(256),
    IN _password char(64),
    IN _firstname varchar(256),
    IN _lastname varchar(256),
    OUT _id varchar(256))
LANGUAGE plpgsql
AS $BODY$
BEGIN
	INSERT INTO "User" (email, password, type, firstname, lastname)
	    VALUES (_email, _password, 'ADMIN', _firstname, _lastname) RETURNING id::varchar INTO _id;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_pickie(
    IN _email varchar(256),
    IN _password char(64),
    IN _firstname varchar(256),
    IN _lastname varchar(256),
    IN _username varchar(256),
    OUT _id varchar(256))
LANGUAGE plpgsql
AS $BODY$
BEGIN
	INSERT INTO "User" (email, password, type, firstname, lastname, username)
	    VALUES (_email, _password, 'PICKIE', _firstname, _lastname, _username) RETURNING id::varchar INTO _id;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_restaurateur(
    IN _email varchar(256),
    IN _password char(64),
    IN _firstname varchar(256),
    IN _lastname varchar(256),
    IN _phone varchar(16),
    IN _ssn varchar(256),
    IN _rest_name varchar(256),
    IN _rest_phone varchar(16),
    IN _rest_address varchar(256),
    OUT _id varchar(256))
LANGUAGE plpgsql
AS $BODY$
DECLARE
	restid UUID;
BEGIN
    INSERT INTO "Restaurant" (name, phone, address) VALUES
        	(_rest_name, _rest_phone, _rest_address) RETURNING id::varchar INTO restid;

    INSERT INTO "User" (email, password, type, firstname, lastname, phone, ssn, fk_restaurant)
        VALUES (_email, _password, 'REST', _firstname, _lastname, _phone, _ssn, restid) RETURNING id::varchar INTO _id;
END;
$BODY$;

CREATE PROCEDURE update_admin(
    IN _id varchar,
    IN _email varchar,
    IN _firstname varchar,
    IN _lastname varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    UPDATE "User" SET
		email=_email,
		firstname=_firstname,
		lastname=_lastname
		WHERE id=_id::uuid;
END;
$BODY$;

CREATE PROCEDURE update_pickie(
    IN _id varchar,
    IN _email varchar,
    IN _firstname varchar,
    IN _lastname varchar,
    IN _username varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    UPDATE "User" SET
		email=_email,
		firstname=_firstname,
		lastname=_lastname,
		username=_username
		WHERE id=_id::uuid;
END;
$BODY$;

CREATE PROCEDURE update_restaurateur(
    IN _id varchar,
    IN _email varchar,
    IN _firstname varchar,
    IN _lastname varchar,
    IN _phone varchar,
    IN _ssn varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    UPDATE "User" SET
		email=_email,
		firstname=_firstname,
		lastname=_lastname,
		phone=_phone,
		ssn=_ssn
		WHERE id=_id::uuid;
END;
$BODY$;

CREATE PROCEDURE update_restaurant(
    IN _id varchar,
    IN _name varchar,
    IN _phone varchar,
    IN _address varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    UPDATE "Restaurant" SET
		name=_name,
		phone=_phone,
		address=_address
		WHERE id=_id::uuid;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_root_ingredient(
    IN _name varchar,
    OUT _id varchar)
LANGUAGE plpgsql
AS $BODY$
BEGIN
	INSERT INTO "Ingredient" (name, fk_parent)
	    VALUES (_name, null) RETURNING id::varchar INTO _id;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_child_ingredient(
    IN _name varchar,
    IN _parent_name varchar,
    OUT _id varchar)
LANGUAGE plpgsql
AS $BODY$
DECLARE
	parent_id UUID;
BEGIN
    SELECT id INTO parent_id FROM "Ingredient" WHERE name = _parent_name::CITEXT;
    IF(parent_id IS NULL) THEN
    		RAISE 'Invalid parent ingredient provided [_parent_name="%"]', _parent_name;
    END IF;
	INSERT INTO "Ingredient" (name, fk_parent)
	    VALUES (_name, parent_id) RETURNING id::varchar INTO _id;
END;
$BODY$;

CREATE PROCEDURE test_database(IN _in character varying, OUT _out character varying)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT id INTO _out FROM (SELECT '1' AS id UNION SELECT '2' AS id) AS t WHERE id=_in;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_dish(
    IN _name varchar(256),
    IN _type varchar(16),
    IN _description varchar(4096),
    IN _restaurant_id varchar(256),
    OUT _id varchar(256))
LANGUAGE plpgsql
AS $BODY$
BEGIN
	INSERT INTO "Dish" (name, type, description, fk_restaurant)
	    VALUES (_name, _type, _description, _restaurant_id::uuid) RETURNING id::varchar INTO _id;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_dish_ingredient(
    IN _dish_name varchar,
    IN _restaurant_id varchar,
    IN _ingredient_name varchar,
    IN _cooked boolean,
    IN _optional boolean)
LANGUAGE plpgsql
AS $BODY$
DECLARE
    ingredient_id UUID;
	dish_id UUID;
BEGIN
    SELECT id INTO dish_id FROM "Dish" WHERE name = _dish_name::CITEXT AND fk_restaurant=_restaurant_id::uuid;
    IF(dish_id IS NULL) THEN
            RAISE 'Invalid dish provided [_dish_name="%", _restaurant_id="%"]', _dish_name, _restaurant_id;
    END IF;
    SELECT id INTO ingredient_id FROM "Ingredient" WHERE name = _ingredient_name::CITEXT;
    IF(ingredient_id IS NULL) THEN
    		RAISE 'Invalid ingredient provided [_ingredient_name="%"]', _ingredient_name;
    END IF;
	INSERT INTO "Dish_Ingredient" (fk_ingredient, fk_dish, cooked, optional)
	    VALUES (ingredient_id, dish_id, _cooked, _optional);
END;
$BODY$;

CREATE OR REPLACE PROCEDURE delete_dish(IN _dish_name varchar,IN _restaurant_id varchar)
LANGUAGE plpgsql
AS $BODY$
DECLARE
	dish_id UUID;
BEGIN
    SELECT id INTO dish_id FROM "Dish" WHERE name = _dish_name::CITEXT AND fk_restaurant=_restaurant_id::uuid;
    IF(dish_id IS NULL) THEN
            RAISE 'Invalid dish provided [_dish_name="%", _restaurant_id="%"]', _dish_name, _restaurant_id;
    END IF;
	DELETE FROM "Dish_Ingredient" WHERE fk_dish = dish_id;
    DELETE FROM "Dish" WHERE id = dish_id;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE unlink_dish_ingredient(IN _dish_name varchar, IN _restaurant_id varchar)
LANGUAGE plpgsql
AS $BODY$
DECLARE
	dish_id UUID;
BEGIN
    SELECT id INTO dish_id FROM "Dish" WHERE name = _dish_name::CITEXT AND fk_restaurant=_restaurant_id::uuid;
    IF(dish_id IS NULL) THEN
        	RAISE 'Invalid dish provided [_dish_name="%", _restaurant_id="%"]', _dish_name, _restaurant_id;
    END IF;
	DELETE FROM "Dish_Ingredient" WHERE fk_dish = dish_id;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE toggle_dish(IN _dish_name varchar,IN _restaurant_id varchar)
LANGUAGE plpgsql
AS $BODY$
DECLARE
	dish_id UUID;
BEGIN
    SELECT id INTO dish_id FROM "Dish" WHERE name = _dish_name::CITEXT AND fk_restaurant=_restaurant_id::uuid;
    IF(dish_id IS NULL) THEN
            RAISE 'Invalid dish provided [_dish_name="%", _restaurant_id="%"]', _dish_name, _restaurant_id;
    END IF;
    UPDATE "Dish" SET active = NOT active WHERE id = dish_id;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE get_dish(
    IN _dish_name varchar,
    IN _restaurant_id varchar,
	OUT _id varchar,
    OUT _description varchar(4096),
    OUT _type varchar(16),
    OUT _active boolean)
LANGUAGE plpgsql
AS $BODY$
DECLARE
	dish_id UUID;
BEGIN
    SELECT id INTO dish_id FROM "Dish" WHERE name = _dish_name::CITEXT AND fk_restaurant=_restaurant_id::uuid;
    IF(dish_id IS NULL) THEN
            RAISE 'Invalid dish provided [_dish_name="%", _restaurant_id="%"]', _dish_name, _restaurant_id;
    END IF;
    SELECT id, description, type, active INTO _id, _description, _type, _active FROM "Dish" WHERE id = dish_id;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE get_ingredient(
    IN _name varchar,
	OUT _id varchar)
LANGUAGE plpgsql
AS $BODY$
BEGIN
    SELECT id INTO _id FROM "Ingredient" WHERE name = _name::CITEXT;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE update_dish(
    IN _old_name varchar,
    IN _restaurant_id varchar,
    IN _new_name varchar(256),
    IN _description varchar(4096),
    IN _type varchar(16))
LANGUAGE plpgsql
AS $BODY$
BEGIN
    UPDATE "Dish" SET name = _new_name, description = _description, type = _type
        WHERE name = _old_name AND fk_restaurant=_restaurant_id::uuid;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_excluded_group(IN _name varchar, OUT _id varchar)
LANGUAGE plpgsql
AS $BODY$
BEGIN
	INSERT INTO "ExcludedGroup" (name) VALUES (_name) RETURNING id::varchar INTO _id;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_excluded_group_ingredient(
    IN _group_id varchar,
    IN _ingredient_name varchar)
LANGUAGE plpgsql
AS $BODY$
DECLARE
    ingredient_id UUID;
BEGIN
    SELECT id INTO ingredient_id FROM "Ingredient" WHERE name = _ingredient_name::CITEXT;
    IF(ingredient_id IS NULL) THEN
    		RAISE 'Invalid ingredient provided [_ingredient_name="%"]', _ingredient_name;
    END IF;

	INSERT INTO "ExcludedGroup_Ingredient" (fk_excluded_group, fk_ingredient) VALUES (_group_id::uuid, ingredient_id);
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_user_excluded_ingredient(
    IN _userid varchar,
    IN _ingredient_name varchar)
LANGUAGE plpgsql
AS $BODY$
DECLARE
    ingredient_id UUID;
BEGIN
    SELECT id INTO ingredient_id FROM "Ingredient" WHERE name = _ingredient_name::CITEXT;
    IF(ingredient_id IS NULL) THEN
    		RAISE 'Invalid ingredient provided [_ingredient_name="%"]', _ingredient_name;
    END IF;

	INSERT INTO "User_ExcludedIngredient" (fk_user, fk_ingredient) VALUES (_userid::UUID, ingredient_id);
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_user_excluded_group(
    IN _userid varchar,
    IN _group_id varchar)
LANGUAGE plpgsql
AS $BODY$
BEGIN
	INSERT INTO "User_ExcludedGroup" (fk_user, fk_excluded_group) VALUES (_userid::UUID, _group_id::UUID);
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_allergy(
    IN _name varchar,
    OUT _id varchar)
LANGUAGE plpgsql
AS $BODY$
BEGIN
	INSERT INTO "Allergy" (name) VALUES (_name) RETURNING id::varchar INTO _id;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_user_allergy(
    IN _userid varchar,
    IN _allergy_id varchar)
LANGUAGE plpgsql
AS $BODY$
BEGIN
	INSERT INTO "User_Allergy" (fk_user, fk_allergy) VALUES (_userid::UUID, _allergy_id::UUID);
END;
$BODY$;


CREATE OR REPLACE PROCEDURE get_excluded_group_id(
    IN _name varchar,
    OUT _id varchar)
LANGUAGE plpgsql
AS $BODY$
BEGIN
	SELECT id INTO _id FROM "ExcludedGroup" WHERE name = _name::CITEXT;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE clear_user_preference(IN _userid varchar)
LANGUAGE plpgsql
AS $BODY$
DECLARE
	dish_id UUID;
BEGIN
	DELETE FROM "User_ExcludedIngredient" WHERE fk_user = _userid::UUID;
    DELETE FROM "User_ExcludedGroup" WHERE fk_user = _userid::UUID;
    DELETE FROM "User_Allergy" WHERE fk_user = _userid::UUID;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE get_allergy(
    IN _name varchar,
	OUT _id varchar)
LANGUAGE plpgsql
AS $BODY$
BEGIN
    SELECT id INTO _id FROM "Allergy" WHERE name = _name::CITEXT;
END;
$BODY$;

CREATE OR REPLACE VIEW all_ingredient AS
SELECT id::varchar AS id, name, fk_parent FROM "Ingredient";

CREATE OR REPLACE VIEW all_allergy AS
SELECT id::varchar AS id, name FROM "Allergy";

CREATE OR REPLACE VIEW all_dish_ingredient AS
    SELECT fk_dish::varchar AS dish_id, name, cooked, optional FROM "Dish_Ingredient"
        JOIN "Ingredient" AS I ON fk_ingredient=I.id;

CREATE OR REPLACE VIEW all_excludedgroup_ingredient AS
    SELECT fk_excluded_group::varchar AS group_id, name, cooked FROM "ExcludedGroup_Ingredient"
        JOIN "Ingredient" AS I ON fk_ingredient=I.id;

CREATE OR REPLACE VIEW all_user_excluded_ingredient AS
    SELECT fk_user::varchar AS userid, name, cooked FROM "User_ExcludedIngredient"
        JOIN "Ingredient" AS I ON fk_ingredient=I.id;

CREATE OR REPLACE VIEW all_user_excludedgroup AS
    SELECT fk_user::varchar AS userid, fk_excluded_group::varchar AS groupid, name AS groupname FROM "User_ExcludedGroup"
        JOIN "ExcludedGroup" AS EG ON fk_excluded_group=EG.id;

CREATE OR REPLACE VIEW all_user_allergy AS
    SELECT fk_user::varchar AS userid, fk_allergy::varchar AS allergy_id, name AS allergy_name FROM "User_Allergy"
        JOIN "Allergy" AS A ON fk_allergy=A.id;


CALL add_restaurateur('lucaR', 'luca', 'Luca', 'Gasperini', '+393332221111', '123456789', 'Pickie Express', '+391112223333', 'Via del buon gusto, 1', null);
CALL add_pickie('lucaP', 'luca', 'Luca', 'Gasperini', 'luca', null);
CALL add_admin('lucaA', 'luca', 'Luca', 'Gasperini', null);

CALL add_root_ingredient('Carne', null);
CALL add_child_ingredient('Manzo', 'Carne', null);
CALL add_child_ingredient('Suino', 'Carne', null);
CALL add_child_ingredient('Pollo', 'Carne', null);
CALL add_child_ingredient('Petto di pollo', 'Pollo', null);
CALL add_root_ingredient('Pesce', null);
CALL add_child_ingredient('Tonno', 'Pesce', null);
CALL add_root_ingredient('Pasta', null);
CALL add_child_ingredient('Rigatoni', 'Pasta', null);
CALL add_root_ingredient('Pane', null);
CALL add_root_ingredient('Alcol', null);

CALL add_excluded_group('HALAL', null);
CALL add_excluded_group('CARNIVORE', null);
CALL add_excluded_group('KOSHER', null);
CALL add_excluded_group('PESCATARIAN', null);
CALL add_excluded_group('VEGAN', null);
CALL add_excluded_group('VEGETARIAN', null);
CALL add_excluded_group('PREGNANT', null);

CALL add_allergy('aaaa', null);
CALL add_allergy('bbbb', null);
CALL add_allergy('cccc', null);

DO $$
DECLARE
	id varchar;
BEGIN
	CALL add_excluded_group('Incinta', id);
	CALL add_excluded_group_ingredient(id, 'Alcol');
END;
$$;