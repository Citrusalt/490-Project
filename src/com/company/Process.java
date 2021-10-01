package com.company;

public class Process implements Runnable{

    double sleepN;
    public Process(double N) {
        sleepN = N;
    }
    public void run(){

        System.out.println("  ...  ...  Slumber thread is sleeping for " + sleepN + " seconds");
        try {
            Thread.sleep((long)(sleepN*1000));  // sleepN needs to be converted to milliseconds
        } catch (InterruptedException ex) {
            // TBD catch and deal with exception ere

        }
        System.out.println("  ...  ...  Slumber thread has woken up ");
    }
}
