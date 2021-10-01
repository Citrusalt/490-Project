package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class MainWindow {

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
    private JTextField CSVEntryField;

    public MainWindow() {

        CSVReader myCSVReader = new CSVReader();
        //Initialize table contents here

        startSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sysStatus.setText("System Running");

                execStatus.setText("exec: running");
                //Call some system run function

                String timeField;
                timeField = timeUnitTextField.getText();
                int timeMultiplier = Integer.parseInt(timeField);
                Process procObject = new Process((double)10 * timeMultiplier/1000); // create a runnable object  that will sleep for 4 seconds
                Thread  mt = new Thread(procObject);    // add this object to a thread and start the thread
                mt.start();

                System.out.println("Started the thread");
                // without the join, either thread can complete before the other
                try {
                    mt.join();  // wait for my thread to complete
                } catch (Exception ex) {
                    // TO DO handle system error here
                }
                System.out.println("Main program exiting");
                System.exit(0);

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


        CSVEntryField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(myCSVReader.createArray(CSVEntryField.getText())); //calls CSVReader's createArray function with string passed in from GUI
            }
        });
    }

    private void createTable(ArrayList<Input> input){
        DefaultTableModel table = new DefaultTableModel(null,new String[]{"Process Name", "Service Time"});
        //This is temporarily hardcoded, this will need to be dynamically assigned at runtime in the future
        for(Input i: input){
            Vector<String> row =new Vector<String>();
            row.add(i.processID);
            row.add(String.valueOf(i.serviceTime));
            table.addRow(row);
        }
         processQueueTable.setModel(table);
    }


    //Constructor
    public void createGUI(){
        JFrame frame = new JFrame("Phase 1 GUI");
        frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
