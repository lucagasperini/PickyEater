CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE "Restaurant" (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    crtime timestamp without time zone NOT NULL DEFAULT now(),
    name varchar(256) NOT NULL,
    phone varchar(16) NOT NULL,
    address varchar(256) NOT NULL,
    city CITEXT NOT NULL,
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

CREATE TABLE "Allergy_Ingredient" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    fk_ingredient UUID NOT NULL,
    fk_allergy UUID NOT NULL,
    unique(fk_allergy, fk_ingredient),
    FOREIGN KEY(fk_ingredient) REFERENCES "Ingredient"(id),
    FOREIGN KEY(fk_allergy) REFERENCES "Allergy"(id)
);


CREATE OR REPLACE PROCEDURE restinfo(
	IN _id varchar,
	OUT _name varchar,
	OUT _phone varchar,
	OUT _address varchar,
	OUT _city varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT name, phone, address, city
		INTO _name, _phone, _address, _city
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

CREATE OR REPLACE PROCEDURE get_user(
	IN _id varchar,
	OUT _email varchar,
	OUT _type varchar,
	OUT _firstname varchar,
	OUT _lastname varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT email, type, firstname, lastname
		INTO _email, _type, _firstname, _lastname
		FROM "User" WHERE id = _id::UUID;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE get_pickie(
	IN _id varchar,
	OUT _email varchar,
	OUT _firstname varchar,
	OUT _lastname varchar,
	OUT _username varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT email, firstname, lastname, username
		INTO _email, _firstname, _lastname, _username
		FROM "User" WHERE id = _id::UUID;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE get_restaurateur(
	IN _id varchar,
	OUT _email varchar,
	OUT _firstname varchar,
	OUT _lastname varchar,
	OUT _phone varchar,
	OUT _ssn varchar,
	OUT _restaurant varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT email, firstname, lastname, phone, ssn, fk_restaurant
		INTO _email, _firstname, _lastname, _phone, _ssn, _restaurant
		FROM "User" WHERE id = _id::UUID;
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
    IN _city varchar(256),
    OUT _id varchar(256))
LANGUAGE plpgsql
AS $BODY$
BEGIN
	INSERT INTO "Restaurant" (name, phone, address, city)
	    VALUES (_name, _phone, _address, _city) RETURNING id::varchar INTO _id;
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
    IN _rest_city varchar(256),
    OUT _id varchar(256))
LANGUAGE plpgsql
AS $BODY$
DECLARE
	restid UUID;
BEGIN
    INSERT INTO "Restaurant" (name, phone, address, city) VALUES
        	(_rest_name, _rest_phone, _rest_address, _rest_city) RETURNING id::varchar INTO restid;

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
    IN _address varchar,
    IN _city varchar)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    UPDATE "Restaurant" SET
		name=_name,
		phone=_phone,
		address=_address,
		city=_city
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
    IN _dish_id varchar,
    IN _ingredient_name varchar,
    IN _cooked boolean,
    IN _optional boolean)
LANGUAGE plpgsql
AS $BODY$
DECLARE
    ingredient_id UUID;
BEGIN
    SELECT id INTO ingredient_id FROM "Ingredient" WHERE name = _ingredient_name::CITEXT;
    IF(ingredient_id IS NULL) THEN
    		RAISE 'Invalid ingredient provided [_ingredient_name="%"]', _ingredient_name;
    END IF;
	INSERT INTO "Dish_Ingredient" (fk_ingredient, fk_dish, cooked, optional)
	    VALUES (ingredient_id, _dish_id::UUID, _cooked, _optional);
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

CREATE OR REPLACE PROCEDURE add_allergy_ingredient(
    IN _allergy_id varchar,
    IN _ingredient_id varchar)
LANGUAGE plpgsql
AS $BODY$
DECLARE
    ingredient_id UUID;
BEGIN
	INSERT INTO "Allergy_Ingredient" (fk_allergy, fk_ingredient) VALUES (_allergy_id::UUID, _ingredient_id::UUID);
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_allergy_ingredient_name(
    IN _allergy_name varchar,
    IN _ingredient_name varchar)
LANGUAGE plpgsql
AS $BODY$
DECLARE
    ingredient_id UUID;
    allergy_id UUID;
BEGIN
    SELECT id INTO ingredient_id FROM "Ingredient" WHERE name = _ingredient_name::CITEXT;
    IF(ingredient_id IS NULL) THEN
    		RAISE 'Invalid ingredient provided [_ingredient_name="%"]', _ingredient_name;
    END IF;
    SELECT id INTO allergy_id FROM "Allergy" WHERE name = _allergy_name::CITEXT;
    IF(allergy_id IS NULL) THEN
            RAISE 'Invalid ingredient provided [_allergy_name="%"]', _allergy_name;
    END IF;

	INSERT INTO "Allergy_Ingredient" (fk_allergy, fk_ingredient) VALUES (allergy_id, ingredient_id);
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

CREATE OR REPLACE VIEW all_city AS
    SELECT DISTINCT city AS name FROM "Restaurant" ORDER BY city;

CREATE OR REPLACE VIEW find_restaurant AS
    SELECT R0.id::varchar AS restaurant_id,
           R0.name AS restaurant_name,
           R0.city::varchar AS restaurant_city,
           R0.phone AS restaurant_phone,
           R0.address AS restaurant_address,
           U0.id::varchar AS user_id
    FROM "Restaurant" AS R0 JOIN
         "Dish" AS D0 ON D0.fk_restaurant = R0.id CROSS JOIN
         "User" AS U0
    WHERE U0.type = 'PICKIE' AND (R0.name, D0.name, D0.type, U0.id) NOT IN (
        SELECT
            R.name AS restaurant_name,
            D.name AS dish_name,
            D.type AS dish_type,
            U.id AS user_id
        FROM "Restaurant" AS R JOIN
            "Dish" AS D ON D.fk_restaurant = R.id JOIN
            "Dish_Ingredient" AS DI ON DI.fk_dish = D.id JOIN
            "Ingredient" AS I ON DI.fk_ingredient = I.id CROSS JOIN
            "User" AS U JOIN
            "User_ExcludedIngredient" AS UEI ON U.id = UEI.fk_user
        WHERE
            U.type = 'PICKIE' AND
            UEI.fk_ingredient = I.id
    UNION
        SELECT
            R.name AS restaurant_name,
            D.name AS dish_name,
            D.type AS dish_type,
            U.id AS user_id
        FROM "Restaurant" AS R JOIN
            "Dish" AS D ON D.fk_restaurant = R.id JOIN
            "Dish_Ingredient" AS DI ON DI.fk_dish = D.id JOIN
            "Ingredient" AS I ON DI.fk_ingredient = I.id CROSS JOIN
            "User" AS U JOIN
            "User_ExcludedGroup" AS UEG ON U.id = UEG.fk_user JOIN
            "ExcludedGroup_Ingredient" AS EGI ON UEG.fk_excluded_group = EGI.fk_excluded_group
        WHERE
            U.type = 'PICKIE' AND
            EGI.fk_ingredient = I.id
    UNION
        SELECT
            R.name AS restaurant_name,
            D.name AS dish_name,
            D.type AS dish_type,
            U.id AS user_id
        FROM "Restaurant" AS R JOIN
            "Dish" AS D ON D.fk_restaurant = R.id JOIN
            "Dish_Ingredient" AS DI ON DI.fk_dish = D.id JOIN
            "Ingredient" AS I ON DI.fk_ingredient = I.id JOIN
        	"Allergy_Ingredient" AS AI ON I.id = AI.fk_ingredient CROSS JOIN
            "User" AS U JOIN
            "User_Allergy" AS UA ON U.id = UA.fk_user
        WHERE
            U.type = 'PICKIE' AND
            UA.fk_allergy = AI.fk_allergy)
    AND D0.type IN ('DRINK', 'APPETIZER', 'FIRST', 'CONTOUR', 'SECOND', 'DESSERT')
    GROUP BY R0.id, R0.name, R0.city, R0.phone, R0.address, U0.id
    HAVING COUNT(DISTINCT D0.type) = 6;

CALL add_restaurateur('lucaR', 'luca', 'Luca', 'Gasperini', '+393332221111', '123456789', 'Pickie Express', '+391112223333', 'Via del buon gusto, 1', 'Roma', null);
CALL add_restaurateur('lucaR1', 'luca', 'Luca', 'Gasperini', '+393332221111', '1111', 'Red', '+391112223333', 'Via del buon gusto, 1', 'Roma', null);
CALL add_restaurateur('lucaR2', 'luca', 'Luca', 'Gasperini', '+393332221111', '2222', 'Green', '+391112223333', 'Via del buon gusto, 1', 'Roma', null);
CALL add_restaurateur('lucaR3', 'luca', 'Luca', 'Gasperini', '+393332221111', '3333', 'Blue', '+391112223333', 'Via del buon gusto, 1', 'Roma', null);
CALL add_pickie('lucaP', 'luca', 'Luca', 'Gasperini', 'lucap', null);
CALL add_pickie('lucaP1', 'luca', 'Luca', 'Gasperini', 'lucap1', null);
CALL add_pickie('lucaP2', 'luca', 'Luca', 'Gasperini', 'lucap2', null);
CALL add_admin('lucaA', 'luca', 'Luca', 'Gasperini', null);

-------------- Ingredient table population -----------------
CALL add_root_ingredient('Alcol', null);
CALL add_root_ingredient('Ingrediente di origine animale', null);
CALL add_child_ingredient('Volatile', 'Ingrediente di origine animale', null);
CALL add_child_ingredient('Pollo', 'Volatile', null);
CALL add_child_ingredient('Petto di pollo', 'Pollo',  null);
CALL add_child_ingredient('Coscia di pollo','Pollo', null);
CALL add_child_ingredient('Gallina','Volatile', null);
CALL add_child_ingredient('Uovo di gallina', 'Gallina', null);
CALL add_child_ingredient('Collo di gallina', 'Gallina', null);
CALL add_child_ingredient('Gallo', 'Volatile', null);
CALL add_child_ingredient('Tacchino', 'Volatile', null);
CALL add_child_ingredient('Quaglia', 'Volatile', null);
CALL add_child_ingredient('Oca', 'Volatile', null);
CALL add_child_ingredient('Fagiano', 'Volatile', null);
CALL add_child_ingredient('Suino', 'Ingrediente di origine animale', null);
CALL add_child_ingredient('Maiale', 'Suino', null);
CALL add_child_ingredient('Coscia di maiale', 'Maiale', null);
CALL add_child_ingredient('Prosciutto cotto', 'Coscia di maiale', null);
CALL add_child_ingredient('Prosciutto crudo', 'Coscia di maiale', null);
CALL add_child_ingredient('Collo di maiale', 'Maiale', null);
CALL add_child_ingredient('Cinghiale', 'Suino', null);
CALL add_child_ingredient('Bovino', 'Ingrediente di origine animale', null);
CALL add_child_ingredient('Vitella', 'Bovino', null);
CALL add_child_ingredient('Vitellone', 'Bovino', null);
CALL add_child_ingredient('Manzo', 'Bovino', null);
CALL add_child_ingredient('Mucca', 'Bovino', null);
CALL add_child_ingredient('Latte di mucca', 'Mucca', null);
CALL add_child_ingredient('Latte intero di mucca', 'Latte di mucca', null);
CALL add_child_ingredient('Latte scremato di mucca', 'Latte di mucca', null);
CALL add_child_ingredient('Parmigiano Reggiano', 'Latte di mucca', null);
CALL add_child_ingredient('Equino', 'Ingrediente di origine animale', null);
CALL add_child_ingredient('Cavallo', 'Equino', null);
CALL add_child_ingredient('Roditore', 'Ingrediente di origine animale', null);
CALL add_child_ingredient('Lepre', 'Roditore', null);
CALL add_child_ingredient('Coniglio', 'Roditore', null);
CALL add_child_ingredient('Anfibio o rettile', 'Ingrediente di origine animale', null);
CALL add_child_ingredient('Rana', 'Anfibio o rettile', null);
CALL add_child_ingredient('Serpente', 'Anfibio o rettile', null);
CALL add_child_ingredient('Ovino', 'Ingrediente di origine animale', null);
CALL add_child_ingredient('Abbacchio', 'Ovino', null);
CALL add_child_ingredient('Pecora', 'Ovino', null);
CALL add_child_ingredient('Pesce', 'Ingrediente di origine animale', null);
CALL add_child_ingredient('Salmone', 'Pesce', null);
CALL add_child_ingredient('Tonno', 'Pesce', null);
CALL add_child_ingredient('Sogliola', 'Pesce', null);
CALL add_child_ingredient('Orata', 'Pesce', null);
CALL add_child_ingredient('Crostaceo', 'Ingrediente di origine animale', null);
CALL add_child_ingredient('Aragosta', 'Crostaceo', null);
CALL add_child_ingredient('Gambero', 'Crostaceo', null);
CALL add_child_ingredient('Mollusco', 'Ingrediente di origine animale', null);
CALL add_child_ingredient('Polpo', 'Mollusco', null);
CALL add_child_ingredient('Seppia', 'Mollusco', null);
CALL add_child_ingredient('Vongola', 'Mollusco', null);
CALL add_child_ingredient('Cozza', 'Mollusco', null);
CALL add_child_ingredient('Lumaca', 'Mollusco', null);
CALL add_child_ingredient('Insetto', 'Ingrediente di origine animale', null);
CALL add_child_ingredient('Miele', 'Insetto', null);
CALL add_root_ingredient('Ingrediente di origine vegetale', null);
CALL add_child_ingredient('Cereale', 'Ingrediente di origine vegetale', null);
CALL add_child_ingredient('Frumento', 'Cereale', null);
CALL add_child_ingredient('Grano tenero', 'Frumento', null);
CALL add_child_ingredient('Farina di grano tenero', 'Grano tenero', null);
CALL add_child_ingredient('Grano duro', 'Frumento', null);
CALL add_child_ingredient('Farina di grano duro', 'Grano duro', null);
CALL add_child_ingredient('Semola di grano duro', 'Grano duro', null);
CALL add_child_ingredient('Cuscus', 'Semola di grano duro', null);
CALL add_child_ingredient('Pasta di grano duro', 'Semola di grano duro', null);
CALL add_child_ingredient('Bulgur', 'Grano duro', null);
CALL add_child_ingredient('Grano saraceno', 'Cereale', null);
CALL add_child_ingredient('Riso', 'Cereale', null);
CALL add_child_ingredient('Riso venere', 'Riso', null);
CALL add_child_ingredient('Riso basmati', 'Riso', null);
CALL add_child_ingredient('Farro', 'Cereale', null);
CALL add_child_ingredient('Avena', 'Cereale', null);
CALL add_child_ingredient('Soia', 'Cereale', null);
CALL add_child_ingredient('Salsa di soia', 'Soia', null);
CALL add_child_ingredient('Olio di semi di soia', 'Soia', null);
CALL add_child_ingredient('Olio di semi di soia raffinato', 'Olio di semi di soia', null);
CALL add_child_ingredient('Germogli di soia', 'Soia', null);
CALL add_child_ingredient('Tofu', 'Soia', null);
CALL add_child_ingredient('Legume', 'Ingrediente di origine vegetale', null);
CALL add_child_ingredient('Lenticchia', 'Legume', null);
CALL add_child_ingredient('Fagiolo', 'Legume', null);
CALL add_child_ingredient('Frutto', 'Ingrediente di origine vegetale', null);
CALL add_child_ingredient('Mela', 'Frutto', null);
CALL add_child_ingredient('Cocomero', 'Frutto', null);
CALL add_child_ingredient('Pesca', 'Frutto', null);
CALL add_child_ingredient('Mango', 'Frutto', null);
CALL add_child_ingredient('Avocado', 'Frutto', null);
CALL add_child_ingredient('Banana', 'Frutto', null);
CALL add_child_ingredient('Fragola', 'Frutto', null);
CALL add_child_ingredient('Oliva', 'Frutto', null);
CALL add_child_ingredient('Olio di oliva', 'Oliva', null);
CALL add_child_ingredient('Olio extravergine di oliva', 'Olio di oliva', null);
CALL add_child_ingredient('Verdura', 'Ingrediente di origine vegetale', null);
CALL add_child_ingredient('Insalata', 'Verdura', null);
CALL add_child_ingredient('Cipolla', 'Verdura', null);
CALL add_child_ingredient('Melanzana', 'Verdura', null);
CALL add_child_ingredient('Tubero', 'Ingrediente di origine vegetale', null);
CALL add_child_ingredient('Patata', 'Tubero', null);
CALL add_child_ingredient('Carota', 'Tubero', null);
CALL add_child_ingredient('Topinambur', 'Tubero', null);
CALL add_child_ingredient('Frutta secca o semi', 'Ingrediente di origine vegetale', null);
CALL add_child_ingredient('Mandorla', 'Frutta secca o semi', null);
CALL add_child_ingredient('Arachide', 'Frutta secca o semi', null);
CALL add_child_ingredient('Noce', 'Frutta secca o semi', null);
CALL add_child_ingredient('Pepe', 'Frutta secca o semi', null);
CALL add_child_ingredient('Pepe rosa', 'Pepe', null);
CALL add_child_ingredient('Pepe nero', 'Pepe', null);
CALL add_child_ingredient('Pepe di Sichuan', 'Pepe', null);
CALL add_root_ingredient('Acqua', null);
CALL add_root_ingredient('Sale', null);
CALL add_root_ingredient('Fungo', null);
CALL add_child_ingredient('Fungo porcino', 'Fungo', null);
CALL add_child_ingredient('Fungo champignon', 'Fungo', null);
CALL add_child_ingredient('Tartufo', 'Fungo', null);
CALL add_child_ingredient('Tartufo bianco', 'Tartufo', null);
CALL add_child_ingredient('Tartufo bianco di Alba', 'Tartufo bianco', null);
CALL add_child_ingredient('Tartufo nero', 'Tartufo', null);
CALL add_child_ingredient('Tartufo nero pregiato', 'Tartufo nero', null);
CALL add_child_ingredient('Tartufo nero estivo', 'Tartufo nero', null);
CALL add_child_ingredient('Tartufo nero invernale', 'Tartufo nero', null);
CALL add_child_ingredient('Tartufo nero liscio', 'Tartufo nero', null);

CALL add_allergy('Glutine', null);
CALL add_allergy('Crostacei', null);
CALL add_allergy('Uova', null);
CALL add_allergy('Pesce', null);
CALL add_allergy('Arachidi', null);
CALL add_allergy('Soia', null);
CALL add_allergy('Latte', null);
CALL add_allergy('Frutta a guscio', null);
CALL add_allergy('Sedano', null);
CALL add_allergy('Senape', null);
CALL add_allergy('Semi di sesamo', null);
CALL add_allergy('Solfiti', null);
CALL add_allergy('Lupini', null);
CALL add_allergy('Molluschi', null);
CALL add_allergy('Grano saraceno', null);
CALL add_allergy('Propoli', null);
CALL add_allergy('Pappa reale', null);
CALL add_allergy('Mango', null);
CALL add_allergy('Pesca', null);
CALL add_allergy('Maiale', null);
CALL add_allergy('Pomodoro', null);
CALL add_allergy('Lattice', null);

CALL add_allergy_ingredient_name('Glutine', 'Frumento');
CALL add_allergy_ingredient_name('Glutine', 'Grano tenero');
CALL add_allergy_ingredient_name('Glutine', 'Grano duro');
CALL add_allergy_ingredient_name('Glutine', 'Farina di grano duro');
CALL add_allergy_ingredient_name('Glutine', 'Semola di grano duro');
CALL add_allergy_ingredient_name('Glutine', 'Cuscus');
CALL add_allergy_ingredient_name('Glutine', 'Pasta di grano duro');
CALL add_allergy_ingredient_name('Glutine', 'Bulgur');
CALL add_allergy_ingredient_name('Glutine', 'Farro');
CALL add_allergy_ingredient_name('Glutine', 'Avena');

CALL add_allergy_ingredient_name('Crostacei', 'Crostaceo');
CALL add_allergy_ingredient_name('Crostacei', 'Aragosta');
CALL add_allergy_ingredient_name('Crostacei', 'Gambero');

CALL add_allergy_ingredient_name('Uova', 'Uovo di gallina');

CALL add_allergy_ingredient_name('Pesce', 'Salmone');
CALL add_allergy_ingredient_name('Pesce', 'Tonno');
CALL add_allergy_ingredient_name('Pesce', 'Sogliola');
CALL add_allergy_ingredient_name('Pesce', 'Orata');

CALL add_allergy_ingredient_name('Arachidi', 'Arachide');

CALL add_allergy_ingredient_name('Soia', 'Soia');
CALL add_allergy_ingredient_name('Soia', 'Salsa di soia');
CALL add_allergy_ingredient_name('Soia', 'Olio di semi di soia');
CALL add_allergy_ingredient_name('Soia', 'Tofu');

CALL add_allergy_ingredient_name('Latte', 'Latte di mucca');
CALL add_allergy_ingredient_name('Latte', 'Latte intero di mucca');
CALL add_allergy_ingredient_name('Latte', 'Latte scremato di mucca');
CALL add_allergy_ingredient_name('Latte', 'Parmigiano Reggiano');

CALL add_allergy_ingredient_name('Frutta a guscio', 'Frutta secca o semi');
CALL add_allergy_ingredient_name('Frutta a guscio', 'Mandorla');
CALL add_allergy_ingredient_name('Frutta a guscio', 'Noce');

CALL add_allergy_ingredient_name('Molluschi', 'Mollusco');
CALL add_allergy_ingredient_name('Molluschi', 'Polpo');
CALL add_allergy_ingredient_name('Molluschi', 'Seppia');
CALL add_allergy_ingredient_name('Molluschi', 'Vongola');
CALL add_allergy_ingredient_name('Molluschi', 'Cozza');
CALL add_allergy_ingredient_name('Molluschi', 'Lumaca');

CALL add_allergy_ingredient_name('Grano saraceno', 'Grano saraceno');

CALL add_allergy_ingredient_name('Propoli', 'Miele');

CALL add_allergy_ingredient_name('Pappa reale', 'Miele');

CALL add_allergy_ingredient_name('Mango', 'Mango');

CALL add_allergy_ingredient_name('Pesca', 'Pesca');

CALL add_allergy_ingredient_name('Maiale', 'Maiale');
CALL add_allergy_ingredient_name('Maiale', 'Coscia di maiale');
CALL add_allergy_ingredient_name('Maiale', 'Prosciutto cotto');
CALL add_allergy_ingredient_name('Maiale', 'Prosciutto crudo');
CALL add_allergy_ingredient_name('Maiale', 'Collo di maiale');

DO $$
DECLARE
	group_id varchar;
BEGIN
    CALL add_excluded_group('HALAL', group_id);
    CALL add_excluded_group('CARNIVORE', group_id);
    CALL add_excluded_group('KOSHER', group_id);
    CALL add_excluded_group('PESCATARIAN', group_id);

    CALL add_excluded_group('VEGAN', group_id);
    CALL add_excluded_group_ingredient(group_id, 'Maiale');
    CALL add_excluded_group_ingredient(group_id, 'Coscia di maiale');
    CALL add_excluded_group_ingredient(group_id, 'Prosciutto cotto');
    CALL add_excluded_group_ingredient(group_id, 'Prosciutto crudo');
    CALL add_excluded_group_ingredient(group_id, 'Collo di maiale');
    CALL add_excluded_group_ingredient(group_id, 'Crostaceo');
    CALL add_excluded_group_ingredient(group_id, 'Aragosta');
    CALL add_excluded_group_ingredient(group_id, 'Gambero');
    CALL add_excluded_group_ingredient(group_id, 'Salmone');
    CALL add_excluded_group_ingredient(group_id, 'Tonno');
    CALL add_excluded_group_ingredient(group_id, 'Sogliola');
    CALL add_excluded_group_ingredient(group_id, 'Orata');
    CALL add_excluded_group_ingredient(group_id, 'Pesce');

    CALL add_excluded_group('VEGETARIAN', group_id);
    CALL add_excluded_group_ingredient(group_id, 'Maiale');
    CALL add_excluded_group_ingredient(group_id, 'Coscia di maiale');
    CALL add_excluded_group_ingredient(group_id, 'Prosciutto cotto');
    CALL add_excluded_group_ingredient(group_id, 'Prosciutto crudo');
    CALL add_excluded_group_ingredient(group_id, 'Collo di maiale');
    CALL add_excluded_group_ingredient(group_id, 'Crostaceo');
    CALL add_excluded_group_ingredient(group_id, 'Aragosta');
    CALL add_excluded_group_ingredient(group_id, 'Gambero');
    CALL add_excluded_group_ingredient(group_id, 'Salmone');
    CALL add_excluded_group_ingredient(group_id, 'Tonno');
    CALL add_excluded_group_ingredient(group_id, 'Sogliola');
    CALL add_excluded_group_ingredient(group_id, 'Orata');
    CALL add_excluded_group_ingredient(group_id, 'Pesce');

    CALL add_excluded_group('PREGNANT', group_id);
	CALL add_excluded_group_ingredient(group_id, 'Alcol');
END;
$$;

DO $$
DECLARE
	restaurant_id varchar;
	dish_id varchar;
	phone varchar;
	address varchar;
BEGIN
    CALL userinfo_rest('lucaR', phone, address, restaurant_id);
	CALL add_dish('a1', 'DRINK', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Maiale', false, false);

    CALL userinfo_rest('lucaR', phone, address, restaurant_id);
	CALL add_dish('a2', 'APPETIZER', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Maiale', false, false);

    CALL userinfo_rest('lucaR', phone, address, restaurant_id);
	CALL add_dish('a3', 'FIRST', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Maiale', false, false);

    CALL userinfo_rest('lucaR', phone, address, restaurant_id);
	CALL add_dish('a4', 'CONTOUR', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Maiale', false, false);

    CALL userinfo_rest('lucaR', phone, address, restaurant_id);
	CALL add_dish('a5', 'SECOND', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Maiale', false, false);

    CALL userinfo_rest('lucaR', phone, address, restaurant_id);
	CALL add_dish('a6', 'DESSERT', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Maiale', false, false);



    CALL userinfo_rest('lucaR1', phone, address, restaurant_id);
	CALL add_dish('b1', 'DRINK', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Alcol', false, false);

    CALL userinfo_rest('lucaR1', phone, address, restaurant_id);
	CALL add_dish('b2', 'APPETIZER', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Alcol', false, false);

    CALL userinfo_rest('lucaR1', phone, address, restaurant_id);
	CALL add_dish('b3', 'FIRST', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Alcol', false, false);

    CALL userinfo_rest('lucaR1', phone, address, restaurant_id);
	CALL add_dish('b4', 'CONTOUR', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Alcol', false, false);

    CALL userinfo_rest('lucaR1', phone, address, restaurant_id);
	CALL add_dish('b5', 'SECOND', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Alcol', false, false);

    CALL userinfo_rest('lucaR1', phone, address, restaurant_id);
	CALL add_dish('b6', 'DESSERT', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Alcol', false, false);



    CALL userinfo_rest('lucaR2', phone, address, restaurant_id);
	CALL add_dish('c1', 'DRINK', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Alcol', false, false);

    CALL userinfo_rest('lucaR2', phone, address, restaurant_id);
	CALL add_dish('c2', 'APPETIZER', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Salmone', false, false);

    CALL userinfo_rest('lucaR2', phone, address, restaurant_id);
	CALL add_dish('c3', 'FIRST', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Salmone', false, false);

    CALL userinfo_rest('lucaR2', phone, address, restaurant_id);
	CALL add_dish('c4', 'CONTOUR', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Salmone', false, false);

    CALL userinfo_rest('lucaR2', phone, address, restaurant_id);
	CALL add_dish('c5', 'SECOND', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Salmone', false, false);

    CALL userinfo_rest('lucaR2', phone, address, restaurant_id);
	CALL add_dish('c6', 'DESSERT', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Salmone', false, false);



    CALL userinfo_rest('lucaR3', phone, address, restaurant_id);
	CALL add_dish('d1', 'DRINK', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Alcol', false, false);

	CALL userinfo_rest('lucaR3', phone, address, restaurant_id);
    CALL add_dish('d2', 'DRINK', '', restaurant_id, dish_id);
    CALL add_dish_ingredient(dish_id, 'Acqua', false, false);

    CALL userinfo_rest('lucaR3', phone, address, restaurant_id);
	CALL add_dish('d3', 'APPETIZER', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Salmone', false, false);

    CALL userinfo_rest('lucaR3', phone, address, restaurant_id);
	CALL add_dish('d4', 'FIRST', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Salmone', false, false);

    CALL userinfo_rest('lucaR3', phone, address, restaurant_id);
	CALL add_dish('d5', 'CONTOUR', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Salmone', false, false);

    CALL userinfo_rest('lucaR3', phone, address, restaurant_id);
	CALL add_dish('d6', 'SECOND', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Salmone', false, false);

    CALL userinfo_rest('lucaR3', phone, address, restaurant_id);
	CALL add_dish('d7', 'DESSERT', '', restaurant_id, dish_id);
	CALL add_dish_ingredient(dish_id, 'Salmone', false, false);

END;
$$;