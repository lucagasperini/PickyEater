package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.model.*;

import java.util.LinkedList;
import java.util.List;

public class EditDishBean extends DishBean {
    private List<DishIngredientBean> ingredientList = new LinkedList<>();
    public EditDishBean(String name, String description, String category) throws BeanException {
        super(name, description,category);
    }
    public List<DishIngredientBean> getIngredientList() {
        return ingredientList;
    }

    public Dish toDish() {
        Dish out = switch (getCategory()) {
            case Dish.TYPE_APPETIZER -> new DishAppetizer();
            case Dish.TYPE_CONTOUR -> new DishContour();
            case Dish.TYPE_DESSERT -> new DishDessert();
            case Dish.TYPE_DRINK -> new DishDrink();
            case Dish.TYPE_SECOND -> new DishSecond();
            case Dish.TYPE_FIRST -> new DishFirst();
            default -> throw new IllegalStateException("Unexpected value: " + getCategory());
        };
        out.setName(getName());
        out.setDescription(getDescription());
        return out;
    }
}
