package com.pickyeaters.logic.model;

public class Ingredient {
    private String id;
    private String name;
    private boolean cooked;
    private boolean optional;
    public Ingredient(String name, boolean cooked, boolean optional) {
        this("", name, cooked, optional);
    }
    public Ingredient(String id, String name) {
        this(id, name, false, false);
    }
    public Ingredient(String id, String name, boolean cooked, boolean optional) {
        setID(id);
        setName(name);
        setCooked(cooked);
        setOptional(optional);
    }


    public String getName() {
        return name;
    }

    public String getID() {
        return id;
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

    public void setID(String id) {
        this.id = id;
    }

    public void setCooked(boolean cooked) {
        this.cooked = cooked;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                '}';
    }
}
