package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientBean {
    private String name;
    private List<IngredientBean> childList = new ArrayList<>();

    public IngredientBean(String name) {
        this.name = name;
    }
    public IngredientBean(Ingredient ingredient) {
        this.name = ingredient.getName();
    }
    public void addChild(IngredientBean item) {
        childList.add(item);
    }

    public List<IngredientBean> getChildList() {
        return childList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
