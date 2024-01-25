CREATE database picky;
CREATE USER picky WITH PASSWORD 'picky';
ALTER DATABASE picky OWNER TO picky;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "User" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    email varchar(256) NOT NULL,
    password char(64) NOT NULL,
    firstname varchar(256) NOT NULL,
    lastname varchar(256) NOT NULL,
    type varchar(16) NOT NULL,
    unique(email)
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


INSERT INTO "User" (email, password, type, firstname, lastname) VALUES ('luca', 'luca', 'ADMIN', 'Luca', 'Gasperini');