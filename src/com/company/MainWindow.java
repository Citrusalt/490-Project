package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Vector;

public class MainWindow {

    private JButton startSystemButton;
    private JButton pauseSystemButton;
    private JPanel mainPanel;
    private JLabel sysStatus;
    private JLabel waitingProcessQueueLabel;
    private JTable processQueueTable;
    private JTextField timeUnitTextField;
    private JLabel timeUnitLabel;
    private JLabel cpuNumbLabel;
    private JLabel execStatus;
    private JLabel timeLabel;
    private JTextField CSVEntryField;
    private JTable processInfoTable;
    private JLabel currentThroughputLabel;

    public MainWindow() {
        System.out.println("MainWindow Constructor");

        //Initialize table contents here


        startSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Pause Button Functionality
        pauseSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sysStatus.setText("System Paused");

                execStatus.setText("exec: idle");

                //call some system pause function
            }
        });

        //File Entry Functionality; creates tables using CSVReader as parser
        CSVEntryField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dispatcher myDispatcher = new Dispatcher(CSVEntryField.getText());
                sysStatus.setText("System Running");

                createQueueTable(myDispatcher.myProcessQueue.Queue);

                execStatus.setText("exec: running");
                //Call some system run function
                String timeField;
                timeField = timeUnitTextField.getText();
                int timeMultiplier = Integer.parseInt(timeField);


                Process procObject = new Process(myDispatcher,(double)timeMultiplier/1000); // create a runnable object  that will sleep for 4 seconds
                Thread  mt = new Thread(procObject);    // add this object to a thread and start the thread

                SwingUtilities.invokeLater(mt);


                mt.start(); //start thread


                System.out.println("Started the thread");
                // without the join, either thread can complete before the other
                try {
                    mt.join();  // wait for my thread to complete
                } catch (Exception ex) {
                    // TO DO handle system error here
                }
                System.out.println("Main program exiting");



            }
        });
    }


    //Creates Process Queue Table
    private void createQueueTable(ArrayList<Input> input){
        DefaultTableModel table = new DefaultTableModel(null,new String[]{"Process Name", "Service Time"});
        for(Input i: input){
            Vector<String> row =new Vector<String>();
            row.add(i.processID);
            row.add(String.valueOf(i.serviceTime));
            table.addRow(row);
        }
         processQueueTable.setModel(table);
    }

    //Creates ProcessInfoTable

    /*
    As of now, the below table just repeats the above logic
    for its creation and will need to be changed in order to
    be dynamic during runtime
     */
    private void createProcessInfoTable(ArrayList<Input> input){
        DefaultTableModel table = new DefaultTableModel(null,new String[]
                {"Process Name", "Arrival Time", "Service Time", "Finish Time", "TAT", "nTAT"});
        //This is temporarily hardcoded, this will need to be dynamically assigned at runtime in the future
        for(Input i: input){
            Vector<String> row =new Vector<String>();
            row.add(i.processID);
            row.add(String.valueOf(i.arrivalTime));
            row.add(String.valueOf(i.serviceTime));
            row.add("X");
            row.add("Y");
            row.add("Z");



            table.addRow(row);
        }
        processInfoTable.setModel(table);
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
