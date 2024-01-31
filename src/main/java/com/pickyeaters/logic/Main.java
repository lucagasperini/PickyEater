package com.pickyeaters.logic;


public class Main  {
    public static void main(String[] args) {
        String gui = args.length > 0 ? args[0] : "";
        if(gui.equals("gui")) {
            MainGUI mainGUI = new MainGUI();
            mainGUI.run(args);
        } else {
            MainCLI mainCLI = new MainCLI();
            mainCLI.run(args);
        }
    }
}
