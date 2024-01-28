package com.pickyeaters.logic.view.cli.restaurateur;

import com.pickyeaters.logic.view.View;
import com.pickyeaters.logic.view.cli.AskAgainView;

public class RestaurateurRemoveIngredientView extends AskAgainView {
    private Boolean answer;
    public void show(){
        showTitle();
        showMessage();
        showQuestion();
        System.out.print("1) ");
        showYesAnswer();
        System.out.println();
        System.out.print("2) ");
        showNoAnswer();
        System.out.println();

        answer = getAnswer();
        if ( answer == true ){


        }
    }
}
