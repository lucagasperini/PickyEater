CREATE database picky;
CREATE USER picky WITH PASSWORD 'picky';
ALTER DATABASE picky OWNER TO picky;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "User" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    username varchar(256) NOT NULL,
    password char(64) NOT NULL,
    firstname varchar(256),
    lastname varchar(256),
    type varchar(16) NOT NULL,
    unique(username)
);

CREATE TABLE "Dish" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    name varchar(256) NOT NULL,
    unique(name)
);

CREATE TABLE "Ingredient" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    crtime TIMESTAMP NOT NULL DEFAULT NOW(),
    name varchar(256) NOT NULL,
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


CREATE OR REPLACE PROCEDURE login(IN username_arg varchar(256), IN password_arg char(64), OUT token_arg varchar(256))
LANGUAGE plpgsql
AS $$
DECLARE
	userid UUID;
BEGIN
    SELECT id INTO userid FROM "User" WHERE username = username_arg AND password = password_arg;
    IF(userid IS NULL) THEN
		token_arg := NULL;
		RETURN;
	END IF;
	INSERT INTO "Session" (fk_user) VALUES (userid) RETURNING token::varchar INTO token_arg;
END;
$$;


INSERT INTO "User" (username, password, type) VALUES ('luca', 'luca', 'ADMIN');