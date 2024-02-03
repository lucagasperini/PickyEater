package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.model.*;

import java.util.LinkedList;
import java.util.List;

public class DishBean {
    private String id;
    private String name;
    private String description;
    private String category;
    private boolean active;
    private List<DishIngredientBean> ingredientList = new LinkedList<>();
    public DishBean() {}
    public DishBean(String id, String name, String description, String category) throws BeanException {
        setID(id);
        setName(name);
        setDescription(description);
        setCategory(category);
    }

    //TODO: check category?
    public DishBean(String name, String description, String category) throws BeanException {
        this("", name, description,category);
    }

    public DishBean(Dish dish) throws BeanException {
        this(dish.getID(), dish.getName(), dish.getDescription(), dish.getType());
        for(Ingredient i : dish.getIngredientList()) {
            ingredientList.add(new DishIngredientBean(i.getName(), i.isCooked(), i.isOptional()));
        }
    }

    public boolean hasIngredients(){
        return !ingredientList.isEmpty();
    }

    public void addIngredient(DishIngredientBean ingredientBean) {
        ingredientList.add(ingredientBean);
    }

    public String getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public List<DishIngredientBean> getIngredientList() {
        return ingredientList;
    }

    public Dish toDish() {
        Dish out = switch (category) {
            case Dish.TYPE_APPETIZER -> new DishAppetizer(name, description);
            case Dish.TYPE_CONTOUR -> new DishContour(name, description);
            case Dish.TYPE_DESSERT -> new DishDessert(name, description);
            case Dish.TYPE_DRINK -> new DishDrink(name, description);
            case Dish.TYPE_SECOND -> new DishSecond(name, description);
            case Dish.TYPE_FIRST -> new DishFirst(name, description);
            default -> throw new IllegalStateException("Unexpected value: " + category);
        };
        out.setID(id);
        out.setActive(active);
        return out;
    }

    public void setName(String name) throws BeanException {
        if (name.isEmpty()) {
            throw new BeanException("DISH_NAME_EMPTY", "Dish cannot be empty");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
