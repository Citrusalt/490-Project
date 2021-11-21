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
        //monitor objects
        private static Object monitor1 = new Object();
        private static Object monitor2 = new Object();
        private static Object monitor3 = new Object();
        public Process(Dispatcher D, Input I, double N, int threadNum, int timeSlice) {
            myDispatcher=D;
            currentProcess=I;
            sleepN=N;
            num=threadNum;
            tSlice=timeSlice;

        }

        @Override
        protected Boolean doInBackground() throws Exception {
            synchronized (monitor1) {//dont let new process start if paused
                try {
                    while (myDispatcher.myMainWindow.pauseVar != 0) {
                        monitor1.wait();
                    }
                } catch (InterruptedException e) {

                }
            }
            System.out.println("Thread " + num + " Started");
            publish(currentProcess);//update GUI elements
            //simulate execution of process


            if(num==1) {
                for(int i=0;i<10;i++) {
                    try {
                        Thread.sleep((long) (currentProcess.serviceTime * sleepN * 1000)/10);  // sleepN needs to be converted to milliseconds, then divide by 10 for amount of iterations
                            synchronized(monitor3) {//interrupt sleeping if pause
                                while (myDispatcher.myMainWindow.pauseVar != 0)
                                    monitor3.wait();
                            }

                    } catch (InterruptedException ex) {

                    }
                }

            }
            else if(num==2){
                for(int i=0;i<10;i++) {
                    try {
                        Thread.sleep((long) (tSlice * sleepN * 1000)/10);  // sleepN needs to be converted to milliseconds, then divide by 10 for amount of iterations
                            synchronized(monitor3) {
                                while (myDispatcher.myMainWindow.pauseVar != 0)
                                    monitor3.wait();
                        }

                    } catch (InterruptedException ex) {

                    }
                }

            }

            synchronized (monitor2) {//dont let current process finish if paused
                try {
                    while (myDispatcher.myMainWindow.pauseVar != 0) {
                        monitor2.wait();
                    }
                } catch (InterruptedException e) {

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


        protected void unpause(){//unpause threads
            synchronized (monitor1) {
                monitor1.notifyAll();
            }
            synchronized (monitor2) {
                monitor2.notifyAll();
            }
            synchronized (monitor3) {
                monitor3.notifyAll();
            }

        }


};

