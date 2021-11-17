package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
/*
* Process Class
* Thread using swing workers
* takes process and simulates it execution
* updates relevant GUI elements using dispatcher functions
*/


public class Process extends SwingWorker<Boolean, Input> {

        private Input currentProcess;//process being executed
        private double sleepN;//time unit
        private Dispatcher myDispatcher;//the dispatcher
        private int num;//1st or 2nd thread
        private int tSlice;//RR timeslice
        private static Object monitor = new Object();

        public Process(Dispatcher D, Input I, double N, int threadNum, int timeSlice) {
            myDispatcher=D;
            currentProcess=I;
            sleepN=N;
            num=threadNum;
            tSlice=timeSlice;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            System.out.println("Thread " + num + " Started");
            publish(currentProcess);//update GUI elements
            //simulate execution of process
            Timer timer=new Timer();
            if(num==1) {

                try {
                    Thread.sleep((long) (currentProcess.serviceTime * sleepN * 1000));  // sleepN needs to be converted to milliseconds
                } catch (InterruptedException ex) {
                    // TBD catch and deal with exception6 er
                }
            }
            else if(num==2){

                try {
                    Thread.sleep((long) (tSlice * sleepN * 1000));  // sleepN needs to be converted to milliseconds
                } catch (InterruptedException ex) {
                    // TBD catch and deal with exception6 er
                }
            }

            return true;
        }

        @Override
        protected void process(List<Input> chunks) {//before simulate execution
            super.process(chunks);

              myDispatcher.ThreadBefore(currentProcess);//update GUI



        }

        @Override
        protected void done() {//when finished
            super.done();
            myDispatcher.ThreadAfter(currentProcess);//update GUI

        }
};

