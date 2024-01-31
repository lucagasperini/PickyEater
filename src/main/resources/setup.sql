CREATE database picky;
CREATE USER picky WITH PASSWORD 'picky';
ALTER DATABASE picky OWNER TO picky;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "Restaurant" (
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    crtime timestamp without time zone NOT NULL DEFAULT now(),
    name character varying(256) NOT NULL,
    phone character varying(16) NOT NULL,
    address character varying(256) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE "User" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    email varchar(256) NOT NULL,
    password char(64) NOT NULL,
    firstname varchar(256) NOT NULL,
    lastname varchar(256) NOT NULL,
    type varchar(16) NOT NULL,
    username character varying(256),
    ssn character varying(256),
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
    name varchar(256) NOT NULL,
    description varchar(4096) NOT NULL,
    fk_restaurant uuid NOT NULL,
    active boolean NOT NULL DEFAULT true,
    FOREIGN KEY (fk_restaurant) REFERENCES "Restaurant" (id)
);

CREATE TABLE "Ingredient" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    name varchar(256) NOT NULL,
    fk_parent uuid,
    FOREIGN KEY (fk_parent) REFERENCES "Ingredient" (id),
    unique(name)
);

CREATE TABLE "Dish_Ingredient" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    fk_ingredient UUID NOT NULL,
    fk_dish UUID NOT NULL,
    unique(fk_dish, fk_ingredient),
    FOREIGN KEY(fk_ingredient) REFERENCES "Ingredient"(id),
    FOREIGN KEY(fk_dish) REFERENCES "Dish"(id)
);

CREATE TABLE "Session" (
    token UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    fk_user UUID NOT NULL
);

CREATE OR REPLACE PROCEDURE restinfo(
	IN _id character varying,
	OUT _name character varying,
	OUT _phone character varying,
	OUT _address character varying)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT name, phone, address
		INTO _name, _phone, _address
		FROM "Restaurant" WHERE id = _id::uuid;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE userinfo(
	IN _email character varying,
	OUT _id character varying,
	OUT _type character varying,
	OUT _firstname character varying,
	OUT _lastname character varying)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT id::varchar, type, firstname, lastname
		INTO _id, _type, _firstname, _lastname
		FROM "User" WHERE email = _email;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE userinfo_pickie(
	IN _email character varying,
	OUT _username character varying)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT username INTO _username FROM "User" WHERE email = _email;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE userinfo_rest(
	IN _email character varying,
	OUT _ssn character varying,
	OUT _restaurant character varying)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    SELECT ssn, fk_restaurant INTO _ssn, _restaurant FROM "User" WHERE email = _email;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE login(IN _email varchar(256), IN _password char(64), OUT _token varchar(256))
LANGUAGE plpgsql
AS $$
DECLARE
	userid UUID;
BEGIN
    SELECT id INTO userid FROM "User" WHERE email = _email AND password = _password;
    IF(userid IS NULL) THEN
		_token := NULL;
		RETURN;
	END IF;
	INSERT INTO "Session" (fk_user) VALUES (userid) RETURNING token::varchar INTO _token;
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

    INSERT INTO "User" (email, password, type, firstname, lastname, ssn, fk_restaurant)
        VALUES (_email, _password, 'REST', _firstname, _lastname, _ssn, restid) RETURNING id::varchar INTO _id;
END;
$BODY$;

CREATE PROCEDURE update_admin(
    IN _id character varying,
    IN _email character varying,
    IN _firstname character varying,
    IN _lastname character varying)
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
    IN _id character varying,
    IN _email character varying,
    IN _firstname character varying,
    IN _lastname character varying,
    IN _username character varying)
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
    IN _id character varying,
    IN _email character varying,
    IN _firstname character varying,
    IN _lastname character varying,
    IN _ssn character varying)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    UPDATE "User" SET
		email=_email,
		firstname=_firstname,
		lastname=_lastname,
		ssn=_ssn
		WHERE id=_id::uuid;
END;
$BODY$;

CREATE PROCEDURE update_restaurant(
    IN _id character varying,
    IN _name character varying,
    IN _phone character varying,
    IN _address character varying)
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
    IN _name character varying,
    OUT _id character varying)
LANGUAGE plpgsql
AS $BODY$
BEGIN
	INSERT INTO "Ingredient" (name, fk_parent)
	    VALUES (_name, null) RETURNING id::varchar INTO _id;
END;
$BODY$;

CREATE OR REPLACE PROCEDURE add_child_ingredient(
    IN _name character varying,
    IN _parent_name character varying,
    OUT _id character varying)
LANGUAGE plpgsql
AS $BODY$
DECLARE
	parent_id UUID;
BEGIN
    SELECT id INTO parent_id FROM "Ingredient" WHERE name = _parent_name;
    IF(parent_id IS NULL) THEN
    		_id := NULL;
    		RETURN;
    END IF;
	INSERT INTO "Ingredient" (name, fk_parent)
	    VALUES (_name, parent_id) RETURNING id::varchar INTO _id;
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
    IN _dish_id varchar(256),
    IN _ingredient_name varchar(256))
LANGUAGE plpgsql
AS $BODY$
DECLARE
	ingredient_id UUID;
BEGIN
    SELECT id INTO ingredient_id FROM "Ingredient" WHERE name = _ingredient_name;
    IF(ingredient_id IS NULL) THEN
    		RETURN;
    END IF;
	INSERT INTO "Dish_Ingredient" (fk_ingredient, fk_dish)
	    VALUES (ingredient_id, _dish_id::uuid);
END;
$BODY$;


CREATE OR REPLACE VIEW all_ingredient AS
SELECT id::varchar AS id, name FROM "Ingredient";

CALL add_restaurateur('lucaR', 'luca', 'Luca', 'Gasperini', '123456789', 'Pickie Express', '+391112223333', 'Via del buon gusto, 1', null);
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
