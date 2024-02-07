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

CREATE TABLE "Allergen" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    name CITEXT NOT NULL,
    unique(name)
);

CREATE TABLE "Allergen_Ingredient" (
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    fk_ingredient UUID NOT NULL,
    fk_allergen UUID NOT NULL,
    unique(fk_allergen, fk_ingredient),
    FOREIGN KEY(fk_ingredient) REFERENCES "Ingredient"(id),
    FOREIGN KEY(fk_allergen) REFERENCES "Allergen"(id)
);

CREATE TABLE "Allergen_Description" (
    id UUID PRIMARY KEY DEFAULT,
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    description CITEXT NOT NULL,
    FOREIGN KEY(id) REFERENCES "Ingredient"(id),
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

CREATE OR REPLACE PROCEDURE get_allergenId(
    IN _name varchar,
	OUT _id varchar)
LANGUAGE plpgsql
AS $BODY$
BEGIN
SELECT id INTO _id FROM "Allergen" WHERE name = _name::CITEXT;
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

-------------- Ingredient table population -----------------
CALL add_root_ingredient('Ingrediente di origine animale', null);
CALL add_child_ingredient('Ingrediente di origine animale', 'Volatile', null);
CALL add_child_ingredient('Volatile', 'Pollo', null);
CALL add_child_ingredient('Pollo', 'Petto di pollo', null);
CALL add_child_ingredient('Pollo', 'Coscia di pollo', null);
CALL add_child_ingredient('Volatile', 'Gallina', null);
CALL add_child_ingredient('Gallina', 'Uovo di gallina', null);
CALL add_child_ingredient('Gallina', 'Collo di gallina', null);
CALL add_child_ingredient('Volatile', 'Gallo', null);
CALL add_child_ingredient('Volatile', 'Tacchino', null);
CALL add_child_ingredient('Volatile', 'Quaglia', null);
CALL add_child_ingredient('Volatile', 'Oca', null);
CALL add_child_ingredient('Volatile', 'Fagiano', null);
CALL add_child_ingredient('Ingrediente di origine animale', 'Suino', null);
CALL add_child_ingredient('Suino', 'Maiale', null);
CALL add_child_ingredient('Maiale', 'Coscia di maiale', null);
CALL add_child_ingredient('Coscia di maiale', 'Prosciutto cotto', null);
CALL add_child_ingredient('Coscia di maiale', 'Prosciutto crudo', null);
CALL add_child_ingredient('Maiale', 'Collo di maiale', null);
CALL add_child_ingredient('Suino', 'Cinghiale', null);
CALL add_child_ingredient('Ingrediente di origine animale', 'Bovino', null);
CALL add_child_ingredient('Bovino', 'Vitella', null);
CALL add_child_ingredient('Bovino', 'Vitellone', null);
CALL add_child_ingredient('Bovino', 'Manzo', null);
CALL add_child_ingredient('Bovino', 'Mucca', null);
CALL add_child_ingredient('Mucca', 'Latte di mucca', null);
CALL add_child_ingredient('Latte di mucca', 'Latte intero di mucca', null);
CALL add_child_ingredient('Latte di mucca', 'Latte scremato di mucca', null);
CALL add_child_ingredient('Latte di mucca', 'Parmigiano Reggiano', null);
CALL add_child_ingredient('Ingrediente di origine animale', 'Equino', null);
CALL add_child_ingredient('Equino', 'Cavallo', null);
CALL add_child_ingredient('Ingrediente di origine animale', 'Roditore', null);
CALL add_child_ingredient('Roditore', 'Lepre', null);
CALL add_child_ingredient('Roditore', 'Coniglio', null);
CALL add_child_ingredient('Ingrediente di origine animale', 'Anfibio o rettile', null);
CALL add_child_ingredient('Anfibio o rettile', 'Rana', null);
CALL add_child_ingredient('Anfibio o rettile', 'Serpente', null);
CALL add_child_ingredient('Ingrediente di origine animale', 'Ovino', null);
CALL add_child_ingredient('Ovino', 'Abbacchio', null);
CALL add_child_ingredient('Ovino', 'Pecora', null);
CALL add_child_ingredient('Ingrediente di origine animale', 'Pesce', null);
CALL add_child_ingredient('Pesce', 'Salmone', null);
CALL add_child_ingredient('Pesce', 'Tonno', null);
CALL add_child_ingredient('Pesce', 'Sogliola', null);
CALL add_child_ingredient('Pesce', 'Orata', null);
CALL add_child_ingredient('Ingrediente di origine animale', 'Crostaceo', null);
CALL add_child_ingredient('Crostaceo', 'Aragosta', null);
CALL add_child_ingredient('Crostaceo', 'Gambero', null);
CALL add_child_ingredient('Ingrediente di origine animale', 'Mollusco', null);
CALL add_child_ingredient('Mollusco', 'Polpo', null);
CALL add_child_ingredient('Mollusco', 'Seppia', null);
CALL add_child_ingredient('Mollusco', 'Vongola', null);
CALL add_child_ingredient('Mollusco', 'Cozza', null);
CALL add_child_ingredient('Mollusco', 'Lumaca', null);
CALL add_child_ingredient('Ingrediente di origine animale', 'Insetto', null);
CALL add_child_ingredient('Insetto', 'Miele', null);
CALL add_root_ingredient('Ingrediente di origine vegetale', null);
CALL add_child_ingredient('Ingrediente di origine vegetale', 'Cereale', null);
CALL add_child_ingredient('Cereale', 'Frumento', null);
CALL add_child_ingredient('Frumento', 'Grano tenero', null);
CALL add_child_ingredient('Grano tenero', 'Farina di grano tenero', null);
CALL add_child_ingredient('Frumento', 'Grano tenero', null);
CALL add_child_ingredient('Frumento', 'Grano duro', null);
CALL add_child_ingredient('Grano duro', 'Farina di grano duro', null);
CALL add_child_ingredient('Grano duro', 'Semola di grano duro', null);
CALL add_child_ingredient('Semola di grano duro', 'Cuscus', null);
CALL add_child_ingredient('Semola di grano duro', 'Pasta di grano duro', null);
CALL add_child_ingredient('Grano duro', 'Bulgur', null);
CALL add_child_ingredient('Cereale', 'Grano saraceno', null);
CALL add_child_ingredient('Cereale', 'Riso', null);
CALL add_child_ingredient('Riso', 'Riso venere', null);
CALL add_child_ingredient('Riso', 'Riso basmati', null);
CALL add_child_ingredient('Cereale', 'Farro', null);
CALL add_child_ingredient('Cereale', 'Avena', null);
CALL add_child_ingredient('Cereale', 'Soia', null);
CALL add_child_ingredient('Soia', 'Salsa di soia', null);
CALL add_child_ingredient('Soia', 'Olio di semi di soia', null);
CALL add_child_ingredient('Olio di semi di soia', 'Olio di semi di soia raffinato', null);
CALL add_child_ingredient('Soia', 'Germogli di soia', null);
CALL add_child_ingredient('Soia', 'Tofu', null);
CALL add_child_ingredient('Ingrediente di origine vegetale', 'Legume', null);
CALL add_child_ingredient('Legume', 'Lenticchia', null);
CALL add_child_ingredient('Legume', 'Fagiolo', null);
CALL add_child_ingredient('Ingrediente di origine vegetale', 'Frutto', null);
CALL add_child_ingredient('Frutto', 'Mela', null);
CALL add_child_ingredient('Frutto', 'Cocomero', null);
CALL add_child_ingredient('Frutto', 'Pesca', null);
CALL add_child_ingredient('Frutto', 'Mango', null);
CALL add_child_ingredient('Frutto', 'Avocado', null);
CALL add_child_ingredient('Frutto', 'Banana', null);
CALL add_child_ingredient('Frutto', 'Fragola', null);
CALL add_child_ingredient('Frutto', 'Oliva', null);
CALL add_child_ingredient('Oliva', 'Olio di oliva', null);
CALL add_child_ingredient('Olio di oliva', 'Olio extravergine di oliva', null);
CALL add_child_ingredient('Ingrediente di origine vegetale', 'Verdura', null);
CALL add_child_ingredient('Verdura', 'Insalata', null);
CALL add_child_ingredient('Verdura', 'Cipolla', null);
CALL add_child_ingredient('Verdura', 'Melanzana', null);
CALL add_child_ingredient('Ingrediente di origine vegetale', 'Tubero', null);
CALL add_child_ingredient('Tubero', 'Patata', null);
CALL add_child_ingredient('Tubero', 'Carota', null);
CALL add_child_ingredient('Tubero', 'Topinambur', null);
CALL add_child_ingredient('Ingrediente di origine vegetale', 'Frutta secca o semi', null);
CALL add_child_ingredient('Frutta secca o semi', 'Mandorla', null);
CALL add_child_ingredient('Frutta secca o semi', 'Arachide', null);
CALL add_child_ingredient('Frutta secca o semi', 'Noce', null);
CALL add_child_ingredient('Frutta secca o semi', 'Pepe', null);
CALL add_child_ingredient('Pepe', 'Pepe rosa', null);
CALL add_child_ingredient('Pepe', 'Pepe nero', null);
CALL add_child_ingredient('Pepe', 'Pepe di Sichuan', null);
CALL add_root_ingredient('Ingrediente non di origine animale o vegetale', null);
CALL add_child_ingredient('Ingrediente non di origine animale o vegetale', 'Acqua', null);
CALL add_child_ingredient('Ingrediente non di origine animale o vegetale', 'Sale', null);
CALL add_child_ingredient('Ingrediente non di origine animale o vegetale', 'Fungo', null);
CALL add_child_ingredient('Fungo', 'Fungo porcino', null);
CALL add_child_ingredient('Fungo', 'Fungo champignon', null);
CALL add_child_ingredient('Fungo', 'Tartufo', null);
CALL add_child_ingredient('Tartufo', 'Tartufo bianco', null);
CALL add_child_ingredient('Tartufo bianco pregiato', 'Tartufo bianco di Alba', null);
CALL add_child_ingredient('Tartufo', 'Tartufo nero', null);
CALL add_child_ingredient('Tartufo nero', 'Tartufo nero pregiato', null);
CALL add_child_ingredient('Tartufo nero', 'Tartufo nero estivo', null);
CALL add_child_ingredient('Tartufo nero', 'Tartufo nero invernale', null);
CALL add_child_ingredient('Tartufo nero', 'Tartufo nero liscio', null);

-------------- Allergen table population -----------------
INSERT INTO "Allergen" (name) VALUES ('Glutine');
INSERT INTO "Allergen" (name) VALUES ('Crostacei');
INSERT INTO "Allergen" (name) VALUES ('Uova');
INSERT INTO "Allergen" (name) VALUES ('Pesce');
INSERT INTO "Allergen" (name) VALUES ('Arachidi');
INSERT INTO "Allergen" (name) VALUES ('Soia');
INSERT INTO "Allergen" (name) VALUES ('Latte');
INSERT INTO "Allergen" (name) VALUES ('Frutta a guscio');
INSERT INTO "Allergen" (name) VALUES ('Sedano');
INSERT INTO "Allergen" (name) VALUES ('Senape');
INSERT INTO "Allergen" (name) VALUES ('Semi di sesamo');
INSERT INTO "Allergen" (name) VALUES ('Solfiti');
INSERT INTO "Allergen" (name) VALUES ('Lupini');
INSERT INTO "Allergen" (name) VALUES ('Molluschi');
INSERT INTO "Allergen" (name) VALUES ('Grano saraceno');
INSERT INTO "Allergen" (name) VALUES ('Propoli');
INSERT INTO "Allergen" (name) VALUES ('Pappa reale');
INSERT INTO "Allergen" (name) VALUES ('Mango');
INSERT INTO "Allergen" (name) VALUES ('Pesca');
INSERT INTO "Allergen" (name) VALUES ('Maiale');
INSERT INTO "Allergen" (name) VALUES ('Pomodoro');
INSERT INTO "Allergen" (name) VALUES ('Lattice');

-------------- Allergen_Description and Allergen_Ingredient table population -----------------
DO $$
DECLARE
_allergenId varchar;
_ingredientId varchar;
BEGIN
-- Glutine
CALL get_allergenId('Glutine', _allergenId);
INSERT INTO "Allergen_Description"(allergenId, description) VALUES (_allergenId, 'Cereali contenenti glutine, vale a dire: grano (tra cui farro e grano khorasan), segale, orzo, avena o i loro ceppi ibridati e prodotti derivati:
a.	sciroppi di glucosio a base di grano, incluso destrosio;
b.	maltodestrine a base di grano;
c.	sciroppi di glucosio a base di orzo;
d.	cereali utilizzati per la fabbricazione di distillati alcolici, incluso l’alcol etilico di origine agricola.');
CALL  get_ingredient('Frumento', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Grano tenero', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Grano duro', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Farina di grano duro', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Semola di grano duro', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Cuscus', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Pasta di grano duro', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Bulgur', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Farro', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Avena', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Crostacei
CALL get_allergenId('Crostacei', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Crostacei e prodotti a base di crostacei.');
CALL  get_ingredient('Crostaceo', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Aragosta', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Gambero', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Uova
CALL get_allergenId('Uova', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Uova e prodotti a base di uova.');
CALL  get_ingredient('Uovo di gallina', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Uova
CALL get_allergenId('Pesce', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Pesce e prodotti della pesca, tranne:
a.	gelatina di pesce utilizzata come supporto per preparati di vitamine o carotenoidi;
b.	gelatina o colla di pesce utilizzata come chiarificante nella birra e nel vino.');
CALL  get_ingredient('Salmone', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Tonno', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Sogliola', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Orata', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Arachidi
CALL get_allergenId('Arachidi', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Arachidi e prodotti a base di arachidi.');
CALL  get_ingredient('Arachide', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Soia
CALL get_allergenId('Soia', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Soia e prodotti a base di soia, tranne:
a.	olio e grasso di soia raffinato;
b.	tocoferoli misti naturali (E306), tocoferolo D-alfa naturale, tocoferolo acetato D-alfa naturale, tocoferolo succinato D-alfa naturale a base di soia;
c.	oli vegetali derivati da fitosteroli e fitosteroli esteri a base di soia;
d.	estere di stanolo vegetale prodotto da steroli di olio vegetale a base di soia.');
CALL  get_ingredient('Soia', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Salsa di soia', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Germogli di soglia', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Olio di semi di soia', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Tofu', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Latte
CALL get_allergenId('Latte', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Latte e prodotti a base di latte (incluso lattosio), tranne:
a.	siero di latte utilizzato per la fabbricazione di distillati alcolici, incluso l’alcol etilico di origine agricola;
b.	lattitolo.');
CALL  get_ingredient('Latte di mucca', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Latte intero di mucca', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Latte scremato di mucca', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Parmigiano Reggiano', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Frutta a guscio
CALL get_allergenId('Frutta a guscio', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Frutta a guscio, vale a dire: mandorle (Amygdalus communis L.), nocciole (Corylus avellana), noci (Juglans regia), noci di acagiù (Anacardium occidentale), noci di pecan [Carya illinoinensis (Wangenh.) K. Koch], noci del Brasile (Bertholletia excelsa), pistacchi (Pistacia vera), noci macadamia o noci del Queensland (Macadamia ternifolia), e i loro prodotti, tranne:
a.	frutta a guscio utilizzata per la fabbricazione di distillati alcolici, incluso l’alcol etilico di origine agricola.');
CALL  get_ingredient('Frutta secca o semi', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Mandorla', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Noce', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Sedano
CALL get_allergenId('Sedano', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Sedano e prodotti a base di sedano.');

-- Senape
CALL get_allergenId('Senape', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Senape e prodotti a base di senape');

-- Semi di sesamo
CALL get_allergenId('Semi di sesamo', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Semi di sesamo e prodotti a base di semi di sesamo');

-- Solfiti
CALL get_allergenId('Solfiti', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Anidride solforosa e solfiti in concentrazioni superiori a 10 mg/kg o 10 mg/litro in termini di SO2 totale da calcolarsi per i prodotti così come proposti pronti al consumo o ricostituiti conformemente alle istruzioni dei fabbricanti.');

-- Lupini
CALL get_allergenId('Lupini', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Lupini e prodotti a base di lupini');

-- Molluschi
CALL get_allergenId('Molluschi', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Molluschi e prodotti a base di molluschi.');
CALL  get_ingredient('Mollusco', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Polpo', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Seppia', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Vongola', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Cozza', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Lumaca', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Sedano
CALL get_allergenId('Grano saraceno', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Grano saraceno e prodotti a base di grano saraceno.');
CALL  get_ingredient('Grano saraceno', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

CALL get_allergenId('Propoli', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Propoli e prodotti a base di propoli.');
CALL  get_ingredient('Miele', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Pappa reale
CALL get_allergenId('Pappa reale', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Pappa reale e prodotti a base di pappa reale.');
CALL  get_ingredient('Miele', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Mango
CALL get_allergenId('Mango', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Mango e prodotti a base di mango.');
CALL  get_ingredient('Mango', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Pesca
CALL get_allergenId('Pesca', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Pesca e prodotti a base di pesca.');
CALL  get_ingredient('Pesca', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Maiale
CALL get_allergenId('Maiale', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Maiale e prodotti a base di maiale.');
CALL  get_ingredient('Maiale', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Coscia di maiale', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Prosciutto cotto', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Prosciutto crudo', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);
CALL  get_ingredient('Collo di maiale', _ingredientId);
INSERT INTO "Allergen_Ingredient"(fk_ingredient, fk_allergen) VALUES (_ingredientId, _allergenId);

-- Pomodoro
CALL get_allergenId('Pomodoro', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Pomodoro e prodotti a base di pomodoro.');

-- Lattice
CALL get_allergenId('Lattice', _allergenId);
INSERT INTO "Allergen_Description"() VALUES('Lattice e prodotti a base di lattice');

END;
$$;


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