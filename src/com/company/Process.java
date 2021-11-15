package com.company;

import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;

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
        private Timer timer; //timer for process
        private Calendar calendar; //calendar for time checking
        private int timeSlice; //

        public Process(Dispatcher D, Input I, double N, int threadNum, int tSlice) {
            myDispatcher=D;
            currentProcess=I;
            sleepN=N;
            num=threadNum;
            timeSlice = tSlice;
        }


        public double GetServiceTime(){
            return currentProcess.serviceTime;
    }

        public void SetServiceTime(double time)
        {
            sleepN = time;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            System.out.println("Thread " + num + " Started");
            publish(currentProcess);//update GUI elements
            //simulate execution of process
            /*
            System.out.println((currentProcess.serviceTime*sleepN) + " is the sleep time");

            calendar = Calendar.getInstance();

            long startTime = System.currentTimeMillis();
            long currentTime = startTime;
            while(currentTime < startTime + ((long)currentProcess.serviceTime*sleepN*1000)){
                //simulated execution time loop
                currentTime = System.currentTimeMillis();
            }
            */
            try {
                Thread.sleep((long)(currentProcess.serviceTime*sleepN*1000));  // sleepN needs to be converted to milliseconds
            } catch (InterruptedException ex) {
                // TBD catch and deal with exception6 er
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

