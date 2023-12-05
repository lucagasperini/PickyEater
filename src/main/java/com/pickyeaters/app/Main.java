package com.pickyeaters.app;

import com.pickyeaters.app.controller.SqlController;

public class Main {
    public static void main(String[] args) {
        System.out.println(SqlController.connect());
        System.out.println("Welcome to PickyEaters");
    }
}
