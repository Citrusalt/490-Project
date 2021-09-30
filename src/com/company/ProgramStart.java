package com.company;

public class ProgramStart {

    public static void main(String[] args){

        CSVGUI myCSVGUI = new CSVGUI();
        myCSVGUI.createCSVGUI();

        //GUI Creation
        MainWindow myGUI = new MainWindow();
        myGUI.createGUI();

    }
}
