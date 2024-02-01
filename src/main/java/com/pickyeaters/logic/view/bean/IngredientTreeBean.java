package com.pickyeaters.logic.view.bean;

public class IngredientTreeBean {
    private final IngredientBean root;

    public IngredientTreeBean(IngredientBean root) {
        this.root = root;
    }

    public IngredientBean getRoot() {
        return root;
    }
}
