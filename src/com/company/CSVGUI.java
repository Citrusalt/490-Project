package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class CSVGUI{
    private JTextField textField1;
    private JLabel csvInstructionLabel;
    private JPanel CSV_Panel;

    JFrame frame = new JFrame("Phase 1 CSV GUI");
    CSVReader myCSVReader = new CSVReader();

    //GUI Creation
    MainWindow myGUI = new MainWindow();


    public CSVGUI() {
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myCSVReader.createArray(textField1.getText()); //calls CSVReader's createArray function with string passed in from GUI
                destroyCVSGUI();
                //Destroy the JFrame object

            }
        });
    }

    public void createCSVGUI(){

        frame.setContentPane(new CSVGUI().CSV_Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    //this function doesn't work for some unknown reason...
    private void destroyCVSGUI(){

        frame.setVisible(false);
        frame.dispose(); //Destroy the JFrame object
        //System.out.println("Destroy Window");
    }


}
