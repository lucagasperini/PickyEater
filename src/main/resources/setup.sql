CREATE database picky;
CREATE USER picky WITH PASSWORD 'picky';
ALTER DATABASE picky OWNER TO picky;

CREATE TABLE "User" (
    id SERIAL PRIMARY KEY,
    username varchar(256) NOT NULL,
    password char(64) NOT NULL,
    type varchar(16) NOT NULL,
    unique(username)
);

CREATE TABLE "Dish" (
    id SERIAL PRIMARY KEY,
    name varchar(256) NOT NULL,
    unique(name)
);

CREATE TABLE "Ingredient" (
    id SERIAL PRIMARY KEY,
    name varchar(256) NOT NULL,
    unique(name)
);

CREATE TABLE "Dish_Ingredient" (
    id SERIAL PRIMARY KEY,
    fk_ingredient INT NOT NULL,
    fk_dish INT NOT NULL,
    unique(fk_dish, fk_ingredient),
    FOREIGN KEY(fk_ingredient) REFERENCES "Ingredient"(id),
    FOREIGN KEY(fk_dish) REFERENCES "Dish"(id)
);