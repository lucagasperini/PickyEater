package com.pickyeaters.logic.view.bean;

public class PreferenceIngredientBean {
    private String name;
    private boolean cooked;
    public PreferenceIngredientBean(String name) {
        this(name, false);
    }
    public PreferenceIngredientBean(String name, boolean cooked) {
        setName(name);
        setCooked(cooked);
    }

    public String getName() {
        return name;
    }

    public boolean isCooked() {
        return cooked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCooked(boolean cooked) {
        this.cooked = cooked;
    }
}
