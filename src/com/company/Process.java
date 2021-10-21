package com.company;

import javax.swing.*;
import java.util.List;

public class Process extends SwingWorker<Boolean, Input> {

        private Input currentProcess;
        private double sleepN;
        private Dispatcher myDispatcher;
        private int num;

        public Process(Dispatcher D, Input I, double N, int threadNum) {
            myDispatcher=D;
            currentProcess=I;
            sleepN=N;
            num=threadNum;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            System.out.println("Thread " + num + " Started");
            publish(currentProcess);
            try {
                Thread.sleep((long)(currentProcess.serviceTime*sleepN*1000));  // sleepN needs to be converted to milliseconds
            } catch (InterruptedException ex) {
                // TBD catch and deal with exception6 er
            }

            return true;
        }

        @Override
        protected void process(List<Input> chunks) {
            super.process(chunks);
          if(num==1){

              myDispatcher.Thread1Before(currentProcess);
          }
          else if(num==2){

              myDispatcher.Thread2Before(currentProcess);
          }

        }

        @Override
        protected void done() {
            super.done();
            if(num==1){
                myDispatcher.Thread1After(currentProcess);
            }
            else if(num==2){
                myDispatcher.Thread2After(currentProcess);
            }
        }
};

