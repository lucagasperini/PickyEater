package com.pickyeaters.logic.view.bean;

public class DishIngredientBean {
    private String name;
    private boolean cooked;
    private boolean optional;

    public DishIngredientBean(String name) {
        this(name, false, false);
    }
    public DishIngredientBean(String name, boolean cooked, boolean optional) {
        setName(name);
        setCooked(cooked);
        setOptional(optional);
    }

    public String getName() {
        return name;
    }

    public boolean isCooked() {
        return cooked;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCooked(boolean cooked) {
        this.cooked = cooked;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }
}
