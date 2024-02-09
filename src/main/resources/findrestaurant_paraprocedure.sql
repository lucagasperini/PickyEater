--IN QUESTO DOCUMENTO CI SONO DELLE "PARA-PROCEDURE" E DELLE "PARA-VISTE" INTERMEDIE 
--CHE HO USATO PER GENERARE LE "PARA-PROCEDURE" PER LO UC FIND RESTAURANT:
----PROCEDURA find_restaurant PER TROVARE I RISTORANTI CHE HANNO TUTTI I PIATTI RICHIESTI DAL PICKIE, 
----PROCEDURA find dish PER TROVARE I PIATTI RICHIESTI DAL PICKIE
--
--TUTTE LE PARAVISTE ANDREBBERO IN VERITà CONVERTITE IN FUNZIONI IN QUANTO IN QUESTO MODO NON FUNZIONA NULLA

---------------------------------------------------------------------------------------------------------------------
---------------------------------PARA-VISTE--------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------
--DISLIKED INGREDIENTS
create view disliked_ingredients as 
select ingredient
from ExcludedGroup_Ingredient
where excludedgroup in (	select excludedgroup
							from User_ExcludedGropu
							where user = var_user
						)					
UNION
select ingredient
from User_ExcludedIngredient
where user = user_var_user
UNION
select ingredient
from Allergy_Ingredient
where allergy in (	select allergy
					from User_Allergy
					where user = var_user);

--RISTORANTI DELLA CITTà
create view city_restaurants as
select id as restaurant
from Restaurant
where city = var_city;

-- PIATTI (VISIBILI) DEI RISTORANTI DELLA CITTà
create view city_active_dishes as
select id as dish
from Dish
where restaurant in (RISTORANTI DELLA CITTà)
	and active = TRUE;

-- PIATTI DEI RISTORANTI DELLA CITTà OK (CON NESSUN INGREDIENTE NO OPPURE ALLERGIA NON OPZIONALE)
create view city_suitable_dishes_view as
select d.id as dish
from Dish d
where d.id in (city_active_dishes)
	and not exists ( 	select di.ingredient
						from Dish_Ingredient di
						where di.dish = d.id
						and di.optional = FALSE
						and (	di.ingredient in (disliked_ingredients)
								or di.id in (	select dishingredient
												from DishIngredient_Allergy dia
												where dia.dishingredient = di.id
												and di.allergy in 	(	select allergy
																		from User_Allergy
																		where user = var_user
																	)
											)
								or not (di.religion = 'entrambe'
										or di.religion in (	select name
															from User_ExcludedGroup ued
																join ExcludedGroup eg on  ueg.excludedgroup = eg.id
															where user = var_user)
										)
							)
					);
					
-- RISTORANTI CHE HANNO PIATTI OK
create view city_suitable_restaurants as
select distinct restaurant
from Dish
where id in (city_suitable_dishes_view)	;


---------------------------------------------------------------------------------------------------------------------
---------------------------------PARA-PROCEDURE------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------
--PROCEDURA: per trovare i nomi e le info dei ristoranti
CREATE OR REPLACE PROCEDURE find_restaurant(
    IN var_user varchar,
	IN var_city varchar,
	IN var_category_appetizer,
	IN var_category_first,
	IN var_category_second,
	IN var_category_side,
	IN var_category_dessert,
	IN var_category_drink,
	OUT var_restaurant_id,
	OUT var_restaurant_name,
	OUT var_restaurant_phone,
	OUT var_restaurant_address)
	
LANGUAGE plpgsql
AS $BODY$
BEGIN
    select 	id into var_restaurant_id,
			name into var_restaurant_name,
			phone into var_restaurant_phone,
			address into var_restaurant_address 
	from Restaurant
	where id in (city_suitable_restaurants)
END;
$BODY$;


--procedura per trovare i piatti di un ristorante che piacciono ad un pickie delle categorie richieste
CREATE OR REPLACE PROCEDURE find_dish(
    IN var_user varchar,
	IN var_restaurant varchar,
	IN var_category_appetizer boolean,
	IN var_category_first boolean,
	IN var_category_second boolean,
	IN var_category_side boolean,
	IN var_category_dessert boolean,
	IN var_category_drink boolean,
	OUT var_dish_id varchar,
	OUT var_dish_name varchar,
	OUT var_dish_description varchar(4096))
	
LANGUAGE plpgsql
AS $BODY$
BEGIN
	select 	d.id into var_dish_id, 
			d.name into var_dish_name, 
			d.description into var_dish_description
	from Dish d
	where d.restaurant = var_restaurant
		and d.active = TRUE
		and (		(var_category_appetizer AND d.type = 'APPETIZER')
				OR 	(var_category_first AND d.type = 'FIRST')
				OR 	(var_category_second AND d.type = 'SECOND')
				OR 	(var_category_side AND d.type = 'SIDE')
				OR 	(var_category_dessert AND d.type = 'DESSERT')
				OR 	(var_category_drink AND d.type = 'DRINK')
			)
		and not exists ( 	select di.ingredient
							from Dish_Ingredient di
							where di.dish = d.id
								and di.optional = FALSE
								and (	di.ingredient in (disliked_ingredients)
										or di.id in (	select dishingredient
														from DishIngredient_Allergy dia
														where dia.dishingredient = di.id
														and di.allergy in 	(	select allergy
																				from User_Allergy
																				where user = var_user
																			)
													)
										or not (di.religion = 'entrambe'
												or di.religion in 	(	select name
																		from User_ExcludedGroup ued
																			join ExcludedGroup eg on  ueg.excludedgroup = eg.id
																		where user = var_user
																	)
												)
									)
						)
END;
$BODY$;


---------------------------------------------------------------------------------------------------------------------
--------------------------------------PROCEDURE------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------
--PROCEDURA: per trovare i nomi e le info dei ristoranti
CREATE OR REPLACE PROCEDURE find_restaurant(
    IN var_user varchar,
	IN var_city varchar,
	IN var_category_appetizer,
	IN var_category_first,
	IN var_category_second,
	IN var_category_side,
	IN var_category_dessert,
	IN var_category_drink,
	OUT var_restaurant_id,
	OUT var_restaurant_name,
	OUT var_restaurant_phone,
	OUT var_restaurant_address)
	
LANGUAGE plpgsql
AS $BODY$
BEGIN
    select 	id into var_restaurant_id,
			name into var_restaurant_name,
			phone into var_restaurant_phone,
			address into var_restaurant_address 
	from Restaurant
	where id in (city_suitable_restaurants)
END;
$BODY$;


--procedura per trovare i piatti di un ristorante che piacciono ad un pickie delle categorie richieste
CREATE OR REPLACE PROCEDURE find_dish(
    IN var_user varchar,
	IN var_restaurant varchar,
	IN var_category_appetizer boolean,
	IN var_category_first boolean,
	IN var_category_second boolean,
	IN var_category_side boolean,
	IN var_category_dessert boolean,
	IN var_category_drink boolean,
	OUT var_dish_id varchar,
	OUT var_dish_name varchar,
	OUT var_dish_description varchar(4096))
	
LANGUAGE plpgsql
AS $BODY$
BEGIN
	select 	d.id into var_dish_id, 
			d.name into var_dish_name, 
			d.description into var_dish_description
	from Dish d
	where d.restaurant = var_restaurant
		and d.active = TRUE
		and (		(var_category_appetizer AND d.type = 'APPETIZER')
				OR 	(var_category_first AND d.type = 'FIRST')
				OR 	(var_category_second AND d.type = 'SECOND')
				OR 	(var_category_side AND d.type = 'SIDE')
				OR 	(var_category_dessert AND d.type = 'DESSERT')
				OR 	(var_category_drink AND d.type = 'DRINK')
			)
		and not exists ( 	select di.ingredient
							from Dish_Ingredient di
							where di.dish = d.id
								and di.optional = FALSE
								and (	di.ingredient in (disliked_ingredients)
										or di.id in (	select dishingredient
														from DishIngredient_Allergy dia
														where dia.dishingredient = di.id
														and di.allergy in 	(	select allergy
																				from User_Allergy
																				where user = var_user
																			)
													)
										or not (di.religion = 'entrambe'
												or di.religion in 	(	select name
																		from User_ExcludedGroup ued
																			join ExcludedGroup eg on  ueg.excludedgroup = eg.id
																		where user = var_user
																	)
												)
									)
						)
END;
$BODY$;