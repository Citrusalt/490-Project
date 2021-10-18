package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
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
    DefaultTableModel queueTable = new DefaultTableModel();
    DefaultTableModel infoTable = new DefaultTableModel();
    private int time1=0;

    public MainWindow() {
        System.out.println("MainWindow Constructor");

        //Table Creation:
        createQueueTable();
        createProcessInfoTable();

        startSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Does nothing as of right now

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

                //Iterate through the Dispatcher's Process Queue to add rows to table
                for (int i = 0; i < myDispatcher.sizeQueue; i++){
                    addRowQueueTable(myDispatcher.myProcessQueue.Queue.get(i));
                }

                execStatus.setText("exec: running");
                //Call some system run function
                String timeField;
                timeField = timeUnitTextField.getText();
                int timeMultiplier = Integer.parseInt(timeField);



                double sleepN = (double)timeMultiplier/1000;

                startThread(myDispatcher, sleepN);

//                Process procObject = new Process(myDispatcher,sleepN); // create a runnable object  that will sleep for 4 seconds
//                Thread  mt = new Thread(procObject);    // add this object to a thread and start the thread
//                mt.start(); //start thread

/*
                Runnable thread = new Runnable() {
                    public void run() {
                        int serviceTime = myDispatcher.PassProcess().serviceTime;
                        System.out.println("  ...  ...  Slumber thread is sleeping for " + serviceTime * sleepN + " seconds");
                        try {
                            Thread.sleep((long) (serviceTime * sleepN * 1000));  // sleepN needs to be converted to milliseconds
                        } catch (InterruptedException ex) {
                            // TBD catch and deal with exception6 er
                        }
                        myDispatcher.RemoveLast();
                        System.out.println("  ...  ...  Slumber thread has woken up ");
                    }
                };
                for (int i =0; i<myDispatcher.sizeQueue;i++ ) {

                    SwingUtilities.invokeLater((thread));

                }

 */





                System.out.println("Started the thread");
                // without the join, either thread can complete before the other
//                try {
//                    mt.join();  // wait for my thread to complete
//                } catch (Exception ex) {
//                    // TO DO handle system error here
//                }
                System.out.println("Main program exiting");



            }
        });
    }

    private void startThread(Dispatcher myDispatcher, double sleepN){


        SwingWorker<Void, Input> worker = new SwingWorker<Void, Input>() {
            @Override
            protected Void doInBackground() throws Exception {



                for (int i =0; i<myDispatcher.sizeQueue;i++ ) {
                    System.out.println("Process " + i + " being executed");
                    int serviceTime = myDispatcher.PassProcess().serviceTime;
                    System.out.println("  ...  ...  Slumber thread is sleeping for " + serviceTime*sleepN+ " seconds");

                    publish(myDispatcher.PassProcess());

                    try {
                        Thread.sleep((long)(serviceTime*sleepN*1000));  // sleepN needs to be converted to milliseconds
                    } catch (InterruptedException ex) {
                        // TBD catch and deal with exception6 er
                    }
                    myDispatcher.RemoveLast();

                }
                System.out.println("  ...  ...  Slumber thread has woken up ");

                return null;
            }

            @Override
            protected void process(List<Input> chunks) {

                addRowProcessInfoTable(chunks.get(chunks.size() - 1));
                removeRowQueueTable((chunks.size() - 1));

            }
        };

        worker.execute();

    }

    //Creates Table with column names
    private void createQueueTable(){
        queueTable.addColumn("Process Name");
        queueTable.addColumn("Service Time");
        processQueueTable.setModel(queueTable);
    }

    //Function to add a row to the Queue Table by passing in an object of type Input
    private void addRowQueueTable(Input myInput)
    {
        Vector<String> row =new Vector<String>();
        row.add(myInput.processID);
        row.add(String.valueOf(myInput.serviceTime));
        queueTable.addRow(row);
    }

    //Function to remove specified row with number
    private void removeRowQueueTable(int i){
        queueTable.removeRow(i);
    }

    //Creates the Process Info Table with column names
    private void createProcessInfoTable(){
        infoTable.addColumn("Process Name");
        infoTable.addColumn("Arrival Time");
        infoTable.addColumn("Service Time");
        infoTable.addColumn("Finish Time");
        infoTable.addColumn("TAT");
        infoTable.addColumn("nTAT");
        processInfoTable.setModel(infoTable);
    }

    //Function to add row with passed in object of type Input
    private void addRowProcessInfoTable(Input myInput){
        Vector<String> row =new Vector<String>();
        row.add(myInput.processID);
        row.add(String.valueOf(myInput.arrivalTime));
        row.add(String.valueOf(myInput.serviceTime));
        row.add(String.valueOf(time1 = time1 + myInput.serviceTime));
        row.add(String.valueOf(time1- myInput.arrivalTime));
        row.add(String.valueOf((time1- myInput.arrivalTime)/ myInput.serviceTime));
        infoTable.addRow(row);
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
