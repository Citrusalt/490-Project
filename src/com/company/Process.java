package com.company;

import javax.swing.*;
import java.util.List;

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
        private static Object sleeper = new Object();
        public Process(Dispatcher D, Input I, double N, int threadNum, int timeSlice) {
            myDispatcher=D;
            currentProcess=I;
            sleepN=N;
            num=threadNum;
            tSlice=timeSlice;

        }

        @Override
        protected Boolean doInBackground() throws Exception {
            synchronized (monitor) {
                try {
                    while (myDispatcher.myMainWindow.pauseVar != 0) {
                        monitor.wait();
                    }
                } catch (InterruptedException e) {

                }
                System.out.println("Thread " + num + " Started");
                publish(currentProcess);//update GUI elements
                //simulate execution of process


                if(num==1) {

                    synchronized (sleeper) {
                        try {
                            sleeper.wait((long) (currentProcess.serviceTime * sleepN * 1000));  // sleepN needs to be converted to milliseconds
                        } catch (InterruptedException ex) {
                            // TBD catch and deal with exception6 er
                        }
                    }
                }
                else if(num==2){
                    synchronized (sleeper) {
                        try {
                            sleeper.wait((long) (tSlice * sleepN * 1000));  // sleepN needs to be converted to milliseconds
                        } catch (InterruptedException ex) {
                            // TBD catch and deal with exception6 er
                        }
                    }
                }

                return true;
            }
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


        protected void unpause(){
            synchronized (monitor) {
                monitor.notifyAll();
            }
        }

};

