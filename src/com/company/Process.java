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

        public Process(Dispatcher D, Input I, double N, int threadNum) {
            myDispatcher=D;
            currentProcess=I;
            sleepN=N;
            num=threadNum;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            System.out.println("Thread " + num + " Started");
            publish(currentProcess);//update GUI elements
            //simulate execution of process
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
          if(num==1){//if 1st thread

              myDispatcher.Thread1Before(currentProcess);//update GUI
          }
          else if(num==2){//if 2nd thread

              myDispatcher.Thread2Before(currentProcess);//update GUI
          }

        }

        @Override
        protected void done() {//when finished
            super.done();
            if(num==1){//if 1st thread
                myDispatcher.Thread1After(currentProcess);//update GUI
            }
            else if(num==2){//if 2 thread
                myDispatcher.Thread2After(currentProcess);//update GUI
            }
        }
};

