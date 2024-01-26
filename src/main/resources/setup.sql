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
    name varchar(256) NOT NULL,
    unique(name)
);

CREATE TABLE "IngredientCategory"
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    crtime timestamp without time zone NOT NULL DEFAULT now(),
    name character varying(256) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE "IngredientTypology"
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    crtime timestamp without time zone NOT NULL DEFAULT now(),
    name character varying(256) NOT NULL,
    fk_category uuid NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (fk_category) REFERENCES "IngredientCategory" (id)
);

CREATE TABLE "Ingredient" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    name varchar(256) NOT NULL,
    fk_typology uuid NOT NULL,
    FOREIGN KEY (fk_typology) REFERENCES "IngredientTypology" (id),
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


INSERT INTO "User" (email, password, username, type, firstname, lastname) VALUES ('lucaP', 'luca', 'luca', 'PICKIE', 'Luca', 'Gasperini');
INSERT INTO "User" (email, password, type, firstname, lastname) VALUES ('lucaA', 'luca', 'ADMIN', 'Luca', 'Gasperini');
WITH rest_id AS (
	INSERT INTO "Restaurant" (name, phone, address) VALUES
	('Pickie Express', '+391112223333', 'Via del buon gusto, 1') RETURNING id
) INSERT INTO "User" (email, password, type, firstname, lastname, ssn, fk_restaurant)
VALUES ('lucaR', 'luca', 'REST', 'Luca', 'Gasperini', '123456789', (select id from rest_id));