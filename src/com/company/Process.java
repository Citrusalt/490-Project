package com.company;

public class Process implements Runnable{

    double sleepN;
    Dispatcher processDispatcher;
    public Process(Dispatcher D, double N) {
        sleepN = N;
        processDispatcher=D;

    }
    public void run(){

        for (int i =0; i<processDispatcher.sizeQueue;i++ ) {
            System.out.println("Process " + i + " being executed");
            int serviceTime = processDispatcher.PassProcess().serviceTime;
            System.out.println("  ...  ...  Slumber thread is sleeping for " + serviceTime*sleepN+ " seconds");
            try {
                Thread.sleep((long)(serviceTime*sleepN*1000));  // sleepN needs to be converted to milliseconds
            } catch (InterruptedException ex) {
                // TBD catch and deal with exception6 er
            }
            processDispatcher.RemoveLast();
        }
        System.out.println("  ...  ...  Slumber thread has woken up ");
    }
}
