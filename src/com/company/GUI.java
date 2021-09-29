package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI{

    private JButton startSystemButton;
    private JButton pauseSystemButton;
    private JPanel mainPanel;
    private JLabel sysStatus;
    private JLabel waitingProcessQueueLabel;
    private JTable processQueueTable;
    private JTextArea systemReportStatus;
    private JTextField timeUnitTextField;
    private JLabel timeUnitLabel;
    private JLabel cpuNumbLabel;
    private JLabel execStatus;
    private JLabel timeLabel;

    public GUI(String title) {

        //Initialize table contents here
        createTable();

        startSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sysStatus.setText("System Running");

                execStatus.setText("exec: running");
                //Call some system run function
            }
        });

        pauseSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sysStatus.setText("System Paused");

                execStatus.setText("exec: idle");
                //call some system pause function
            }
        });


    }

    private void createTable(){

        //This is temporarily hardcoded, this will need to be dynamically assigned at runtime in the future
        Object[][] data = {
                //{column 1, column 2}
                {"Process A", 10}, //row 1
                {"Process B", 14}, //row 2
                {"Process C", 4} //row 3
                //{"Process X, X} //row X
        };
        processQueueTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Process Name", "Service Time"} //number of entered strings is number of columns
        ));
    }

    public static void main(String[] args){

        JFrame frame = new JFrame("Phase 1 GUI");
        frame.setContentPane(new GUI("Phase 1 GUI").mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
}
