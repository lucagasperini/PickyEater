package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.model.Allergen;

import java.util.List;
import java.util.Vector;

public class DishBean {
    private String name;
    private String description;
    private String dishType;
    private List<String> ingredients;
    private List<String> allergens[];

    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description = name;
    }
    public void setDishType(String dishType){
        this.dishType = dishType;
    }
    public void setIngredients(List<String> ingredients){
        this.ingredients = ingredients;
    }

    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public String getDishType(){
        return this.dishType;
    }
    public List<String> getIngredients(){
        return this.ingredients;
    }
}
