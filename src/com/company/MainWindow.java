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
    private JTable processQueueTable2;
    private JTable processInfoTable2;
    private JLabel currentThroughputLabel2;
    private JTextField timeSliceLabel;
    DefaultTableModel queueTable = new DefaultTableModel();
    DefaultTableModel infoTable = new DefaultTableModel();
    DefaultTableModel queueTable2 = new DefaultTableModel();
    DefaultTableModel infoTable2 = new DefaultTableModel();
    public int time1=0;
    public double sleepN=0;
    public int completedProcesses1 = 0;
    public int completedProcesses2 = 0;
    DecimalFormat decimalFormat=new DecimalFormat("#.000");
    Timer timer1;
    Timer timer2;
    Timer rrTimer;
    public int pauseVar=0;
    Dispatcher D1;
    Dispatcher D2;
    private double t1;
    private double t2;
    public int rrT;
    private boolean hasStarted = false;


    public MainWindow() {
        System.out.println("MainWindow Constructor");

        //Table Creation:
        createQueueTable();
        createProcessInfoTable();
        createProcessInfoTable2();
        createQueueTable2();

        //Allow start button to begin system processes
        startSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!hasStarted){

                    hasStarted = true;

                    String timeField;
                    timeField = timeUnitTextField.getText();
                    int timeMultiplier = Integer.parseInt(timeField);
                    sleepN = (double)timeMultiplier/1000;
                    D1=new Dispatcher(CSVEntryField.getText(), MainWindow.this, sleepN, 1,-1 );
                    D2=new Dispatcher(CSVEntryField.getText(), MainWindow.this, sleepN, 2,Integer.parseInt(timeSliceLabel.getText()));

                    sysStatus.setText("System Running");

                    //Iterate through the Dispatcher's Process Queue to add rows to table

                    //Call some system run function
                    setExecStatus1("Finished");
                    timeLabel1.setText("time remaining: 0");
                    setExecStatus2("Finished");
                    timeLabel2.setText("time remaining: 0");

                    rrTimer = new Timer();
                    rrTimer.scheduleAtFixedRate(new TimerTask() {

                        @Override
                        public void run() {

                            rrT = rrT + 1;
                        }
                    }, 0, timeMultiplier);

                }



            }
        });

        //Pause Button Functionality
        pauseSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (hasStarted){
                    sysStatus.setText("System Running");
                    String timeField;
                    timeField = timeUnitTextField.getText();
                    int timeMultiplier = Integer.parseInt(timeField);

                    //call system pause function

                    //sleep the thread as a "pause" for a long period of time
                    if(pauseVar==0) {
                        pauseVar=1;
                        sysStatus.setText("System Paused");
                        pauseSystemButton.setText("Run");
                        timer1.cancel();
                        timer2.cancel();
                        rrTimer.cancel();
                    }
                    else if(pauseVar==1){
                        pauseVar=0;
                        sysStatus.setText("System Running");
                        pauseSystemButton.setText("Pause");
                        D1.Thread.unpause();
                        D2.Thread.unpause();
                        timer1= new Timer();
                        timer2= new Timer();
                        rrTimer= new Timer();

                        timer1.scheduleAtFixedRate(new TimerTask() {

                            @Override
                            public void run() {
                                if (t1 >= 0) {
                                    timeLabel1.setText("time remaining:" + decimalFormat.format(t1--));
                                } else {
                                    timeLabel1.setText("time remaining: 0");
                                }
                            }
                        }, 0, 1000);
                        timer2.scheduleAtFixedRate(new TimerTask() {

                            @Override
                            public void run() {
                                if (t2 >= 0) {
                                    timeLabel2.setText("time remaining:" + decimalFormat.format(t2--));
                                } else {
                                    timeLabel2.setText("time remaining: 0");
                                }
                            }
                        }, 0, 1000);

                        rrTimer.scheduleAtFixedRate(new TimerTask() {

                            @Override
                            public void run() {

                                rrT = rrT + 1;
                            }
                        }, 0, timeMultiplier);


                    }
                    //tried to interrupt as a form of resume, but it didn't act right
                    //this also only pauses GUI for some reason and not processes
                    return;
                }

                }

        });

        //File Entry Functionality; creates tables using CSVReader as parser
        CSVEntryField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


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

    //Creates Table with column names
    private void createQueueTable2(){
        queueTable2.addColumn("Process Name");
        queueTable2.addColumn("Service Time");
        processQueueTable2.setModel(queueTable2);
    }

    //Function to add a row to the Queue Table by passing in an object of type Input
    public void addRowQueueTable(Input myInput)
    {
        Vector<String> row =new Vector<String>();
        row.add(myInput.processID);
        row.add(String.valueOf(myInput.serviceTime));
        queueTable.addRow(row);
    }
    public void addRowQueueTable2(Input myInput)
    {
        Vector<String> row =new Vector<String>();
        row.add(myInput.processID);
        row.add(String.valueOf(myInput.serviceTime));
        queueTable2.addRow(row);
    }

    //Function to remove specified row with number
    public void removeRowQueueTable(int i){
        queueTable.removeRow(i);
    }
    public void removeRowQueueTable2(int i){
        queueTable2.removeRow(i);
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
    //Creates the Process Info Table #2 with column names
    private void createProcessInfoTable2(){
        infoTable2.addColumn("Process Name");
        infoTable2.addColumn("Arrival Time");
        infoTable2.addColumn("Service Time");
        infoTable2.addColumn("Finish Time");
        infoTable2.addColumn("TAT");
        infoTable2.addColumn("nTAT");
        processInfoTable2.setModel(infoTable2);
    }

    //Function to add row with passed in object of type Input

    public void addRowProcessInfoTable(Input myInput){
        Vector<String> row =new Vector<String>();
        row.add(myInput.processID);
        row.add(String.valueOf(myInput.arrivalTime));
        row.add(String.valueOf(myInput.serviceTime));
        row.add(String.valueOf(time1 = time1 + myInput.serviceTime));
        row.add(String.valueOf(time1- myInput.arrivalTime));
        row.add(String.valueOf((float)(time1- myInput.arrivalTime)/ myInput.serviceTime));
        infoTable.addRow(row);
    }
    public void addRowProcessInfoTable2(Input myInput){
        completedProcesses2++;
        Vector<String> row =new Vector<String>();
        row.add(myInput.processID);
        row.add(String.valueOf(myInput.arrivalTime));//arrival time
        row.add(String.valueOf(myInput.serviceTime));//service time
        row.add(String.valueOf(rrT));//finish time
        row.add(String.valueOf(rrT- myInput.arrivalTime));//TAT
        row.add(String.valueOf((float)(rrT- myInput.arrivalTime)/ myInput.serviceTime));//nTAT
        infoTable2.addRow(row);
    }
    //updates throughput
    public void updateThroughput(){

        completedProcesses1++;
        double throughput = (double)completedProcesses1/(time1 * sleepN); //calculate throughput
        currentThroughputLabel.setText("Current Throughput: " + throughput);
    }
    //updates throughput
    public void updateThroughput2(){

        double throughput = (double)completedProcesses2/(rrT * sleepN); //calculate throughput
        currentThroughputLabel2.setText("Current Throughput: " + throughput);
    }
    //updates timelabel1
    public void setTimeLabel1(int servicetime, double sleepN){
        t1=servicetime*sleepN;
        timer1=new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (t1 >= 0) {
                    timeLabel1.setText("time remaining:" + decimalFormat.format(t1--));
                } else {
                    timeLabel1.setText("time remaining: 0");
                }
            }
        }, 0, 1000);

    }
    //updates timelabel2
    public void setTimeLabel2(int servicetime, double sleepN){
        t2=servicetime*sleepN;
        timer2=new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (t2 >= 0) {
                    timeLabel2.setText("time remaining:" + decimalFormat.format(t2--));
                } else {
                    timeLabel2.setText("time remaining: 0");
                }
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
