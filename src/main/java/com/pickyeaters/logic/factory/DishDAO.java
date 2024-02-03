package com.pickyeaters.logic.factory;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.exception.DAOException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.model.*;
import com.pickyeaters.logic.view.bean.DishIngredientBean;

import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

public class DishDAO {
    private static final DishDAO instance = new DishDAO();

    private DishDAO() {}

    public static DishDAO getInstance() {
        return instance;
    }

    public void update(Dish dish) throws DAOException {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().query("CALL update_dish(?, ?, ?, ?)");
            query.setString(dish.getID());
            query.setString(dish.getName());
            query.setString(dish.getDescription());
            query.setString(dish.getType());

            query.execute();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public Dish get(String name, String restaurantID) throws DAOException {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().query("CALL get_dish(?, ?, ?, ?, ?, ?)");
            query.setString(name);
            query.setString(restaurantID);
            query.registerOutParameter(Types.VARCHAR);
            query.registerOutParameter(Types.VARCHAR);
            query.registerOutParameter(Types.VARCHAR);
            query.registerOutParameter(Types.BIT);

            query.execute();

            String id = query.getString();
            String description = query.getString();
            String type = query.getString();
            boolean active = query.getBoolean();

            query.close();

            Dish dish = rowToDish(id, name, description, type, active);
            dish.addIngredientList(IngredientDAO.getInstance().getIngredientListOfDish(id));
            return dish;
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public String addDish(String restaurantID, Dish dish) throws DAOException {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().query("CALL add_dish(?, ?, ?, ?, ?)");
            query.setString(dish.getName());
            query.setString(dish.getType());
            query.setString(dish.getDescription());
            query.setString(restaurantID);
            query.registerOutParameter(Types.VARCHAR);

            query.execute();

            String id = query.getString();
            query.close();

            return id;
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }
    public void addDishIngredient(
            String name,
            String restaurantID,
            String ingredientName,
            boolean ingredientCooked,
            boolean ingredientOptional) throws DAOException {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().query("CALL add_dish_ingredient(?, ?, ?, ?, ?)");
            query.setString(name);
            query.setString(restaurantID);
            query.setString(ingredientName);
            query.setBoolean(ingredientCooked);
            query.setBoolean(ingredientOptional);

            query.execute();
            query.close();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public List<Dish> getRestaurantDishList(String restaurantID) throws DAOException {
        try {
            LinkedList<Dish> out = new LinkedList<>();
            DatabaseController.Query query = DatabaseController.getInstance().queryResultSet(
                    "SELECT id, name, description, type, active FROM \"Dish\" WHERE fk_restaurant::varchar = ? ORDER BY name"
            );
            query.setString(restaurantID);
            query.execute();
            while(query.next()) {
                out.add(rowToDish(
                        query.getString(),
                        query.getString(),
                        query.getString(),
                        query.getString(),
                        query.getBoolean()
                ));
            }
            query.close();
            return out;
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    private Dish rowToDish(String id, String name, String description, String type, boolean active) {
        Dish out = switch (type) {
            case Dish.TYPE_APPETIZER -> new DishAppetizer(name, description);
            case Dish.TYPE_CONTOUR -> new DishContour(name, description);
            case Dish.TYPE_DESSERT -> new DishDessert(name, description);
            case Dish.TYPE_DRINK -> new DishDrink(name, description);
            case Dish.TYPE_SECOND -> new DishSecond(name, description);
            case Dish.TYPE_FIRST -> new DishFirst(name, description);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
        out.setID(id);
        out.setActive(active);
        return out;
    }

    public void delete(String name, String restaurantID) throws DAOException {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().query("CALL delete_dish(?, ?)");
            query.setString(name);
            query.setString(restaurantID);
            query.execute();
            query.close();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public void toggle(String name, String restaurantID) throws DAOException {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().query("CALL toggle_dish(?, ?)");
            query.setString(name);
            query.setString(restaurantID);
            query.execute();
            query.close();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }

    public void unlinkIngredient(String name, String restaurantID) throws DAOException {
        try {
            DatabaseController.Query query = DatabaseController.getInstance().query("CALL unlink_dish_ingredient(?, ?)");
            query.setString(name);
            query.setString(restaurantID);
            query.execute();
            query.close();
        } catch (DatabaseControllerException ex) {
            throw new DAOException(ex);
        }
    }
}
