package com.pickyeaters.logic.model;

public class Ingredient {
    private String id;
    private String name;
    public Ingredient(String name) {
        this(null, name);
    }
    public Ingredient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ingredient(Ingredient ingredient) {
        this.id = ingredient.id;
        this.name = ingredient.name;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                '}';
    }
}
