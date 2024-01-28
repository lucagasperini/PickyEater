package com.pickyeaters.logic.view.cli.restaurateur;

import com.pickyeaters.logic.view.View;
import com.pickyeaters.logic.view.bean.DishBean;

import javax.swing.*;
import java.util.Scanner;

public class RestaurateurAddDishView extends View {
    private String titleText;
    private String subtitleText;
    private String nameText;
    private String descriptionText;
    private String categoryText;
    private String ingredientsText;
    private String allergensText;
    private String addIngredientText;
    private String saveChangesText;
    private String backText;
    //buttons
    private JButton backButton;
    private JButton saveButton;
    private JButton reportDish;
    /*private Scanner scanner;
    private DishBean dish;

    public void editName(){
        System.out.printf("%s: ", nameText);
        String name = scanner.next();
        dish.setName(name);
    }
    public void editDescription(){
        System.out.printf("%s: ", descriptionText);
        String description = scanner.next();
        dish.setDescription(description);
    }
    public void editDishType(){
        System.out.printf("%s: ", categoryText);
        String dishType = scanner.next();
        dish.setDishType(dishType);
    }

    public void editIngredients(){
        System.out.printf("%s%n", ingredientsText);
        int i =1;
        for(String ingredient : dish.getIngredients()){
            System.out.printf("%d) %s%n", ingredient);
        }
        System.out.println("What");
    }
    public void showTitle(){
        System.out.printf("%s", titleText);
    }*/
}
