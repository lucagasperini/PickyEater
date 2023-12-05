package com.pickyeaters.app.model;

public abstract class Dish {
    public static final String TYPE_APPETIZER = "APPETIZER";
    public static final String TYPE_DRINK = "DRINK";
    public static final String TYPE_FIRST = "FIRST";
    public static final String TYPE_SECOND = "SECOND";
    public static final String TYPE_CONTOUR = "CONTOUR";
    public static final String TYPE_DESSERT = "DESSERT";

    private String name;
    private int id;
    private Ingredient[] ingredientList;
    protected String type;

    protected Dish(int id, String name, Ingredient[] ingredientList) {
        this.id = id;
        this.name = name;
        this.ingredientList = ingredientList;
    }
    public static String[] getAllType() {

        return new String[]{
                TYPE_APPETIZER,
                TYPE_DRINK,
                TYPE_FIRST,
                TYPE_SECOND,
                TYPE_CONTOUR,
                TYPE_DESSERT
        };
    }
}
