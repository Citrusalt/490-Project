package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.TimerTask;
import java.util.Vector;
import java.util.Timer;

/*
* Class MainWindow
* Is the GUI
* instantiates Dispatcher
* displays both tables, results, etc
* has update functions for GUI elements
*/
public class MainWindow{

    private JButton startSystemButton;
    private JButton pauseSystemButton;
    private JLabel waitingProcessQueueLabel;
    private JLabel cpuNumbLabel1;
    private JLabel cpuNumbLabel2;
    private JLabel timeUnitLabel;
    private JPanel mainPanel;
    private JLabel sysStatus;
    public JTable processQueueTable;
    private JTextField timeUnitTextField;
    public JLabel execStatus1;
    public JLabel timeLabel1;
    private JTextField CSVEntryField;
    public JTable processInfoTable;
    private JLabel currentThroughputLabel;
    public JLabel timeLabel2;
    public JLabel execStatus2;
    DefaultTableModel queueTable = new DefaultTableModel();
    DefaultTableModel infoTable = new DefaultTableModel();
    private int time1=0;
    public double sleepN=0;
    public int completedProcesses = 0;
    DecimalFormat decimalFormat=new DecimalFormat("#.000");
    Timer timer1=new Timer();
    Timer timer2=new Timer();

    public MainWindow() {
        System.out.println("MainWindow Constructor");

        //Table Creation:
        createQueueTable();
        createProcessInfoTable();

        startSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String timeField;
                timeField = timeUnitTextField.getText();
                int timeMultiplier = Integer.parseInt(timeField);
                sleepN = (double)timeMultiplier/1000;
                new Dispatcher(CSVEntryField.getText(), MainWindow.this, sleepN );

                sysStatus.setText("System Running");

                //Iterate through the Dispatcher's Process Queue to add rows to table

                //Call some system run function
                setExecStatus1("Finished");
                timeLabel1.setText("time remaining: 0");
                setExecStatus2("Finished");
                timeLabel2.setText("time remaining: 0");
            }
        });

        //Pause Button Functionality
        pauseSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sysStatus.setText("System Paused");

                execStatus1.setText("exec: idle");

                //call some system pause function
            }
        });

        //File Entry Functionality; creates tables using CSVReader as parser
        CSVEntryField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String timeField;
                timeField = timeUnitTextField.getText();
                int timeMultiplier = Integer.parseInt(timeField);
                sleepN = (double)timeMultiplier/1000;
                new Dispatcher(CSVEntryField.getText(), MainWindow.this, sleepN );

                sysStatus.setText("System Running");

                //Iterate through the Dispatcher's Process Queue to add rows to table

                //Call some system run function
                setExecStatus1("Finished");
                timeLabel1.setText("time remaining: 0");
                setExecStatus2("Finished");
                timeLabel2.setText("time remaining: 0");


            }
        });
        CSVEntryField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    CSVEntryField.setText("");
            }
        });
    }



    //Creates Table with column names
    private void createQueueTable(){
        queueTable.addColumn("Process Name");
        queueTable.addColumn("Service Time");
        processQueueTable.setModel(queueTable);
    }

    //Function to add a row to the Queue Table by passing in an object of type Input

    public void addRowQueueTable(Input myInput)
    {
        Vector<String> row =new Vector<String>();
        row.add(myInput.processID);
        row.add(String.valueOf(myInput.serviceTime));
        queueTable.addRow(row);
    }

    //Function to remove specified row with number

    public void removeRowQueueTable(int i){
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

    public void addRowProcessInfoTable(Input myInput){
        Vector<String> row =new Vector<String>();
        row.add(myInput.processID);
        row.add(String.valueOf(myInput.arrivalTime));
        row.add(String.valueOf(myInput.serviceTime));
        row.add(String.valueOf(time1 = time1 + myInput.serviceTime));
        row.add(String.valueOf(time1- myInput.arrivalTime));
        row.add(String.valueOf((time1- myInput.arrivalTime)/ myInput.serviceTime));
        infoTable.addRow(row);
    }
    //updates throughput
    public void updateThroughput(){

        completedProcesses++;

        double throughput = (double)completedProcesses/(time1 * sleepN); //calculate throughput

        currentThroughputLabel.setText("Current Throughput: " + throughput);

    }
    //updates timelabel1
    public void setTimeLabel1(int servicetime, double sleepN){

        timer1.scheduleAtFixedRate(new TimerTask() {
            double z=servicetime*sleepN;
            @Override
            public void run() {
                timeLabel1.setText("time remaining:" + decimalFormat.format(z--));
            }
        }, 0, 1000);

    }
    //updates timelabel2
    public void setTimeLabel2(int servicetime, double sleepN){

        timer2.scheduleAtFixedRate(new TimerTask() {
            double z=servicetime*sleepN;
            @Override
            public void run() {
                timeLabel2.setText("time remaining:" + decimalFormat.format(z--));

            }
        }, 0, 1000);

    }
    //updates exec status 1
    public void setExecStatus1(String processID){
        execStatus1.setText("Process: " +processID);
    }
    //updates exec status 1
    public void setExecStatus2(String processID){
        execStatus2.setText("Process: " +processID);
    }

    //Constructor
    public void createGUI(){
        JFrame frame = new JFrame("Phase 1 GUI");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
