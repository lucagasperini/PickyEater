package com.pickyeater.cli;

import com.pickyeater.cli.controller.SqlController;

public class Main {
    public static void main(String[] args) {
        System.out.println(SqlController.connect());

    }
}
