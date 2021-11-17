package com.company;

import java.util.ArrayList;


/*
* Class Dispatcher
* makes Process Queue
* makes threads
* provides update functions for the GUI via MainWindow instance
* continue doing processes until no more
*/
public class Dispatcher {

    MainWindow myMainWindow;// MainWindow instance
    protected ProcessQueue myProcessQueue;//singleton process queue
    protected ArrayList<Input> queueCopy;
    double sleepN;//time unit
    private Process Thread;//first thread
    int threadNum;
    int timeSlice;

    public Dispatcher(String filename, MainWindow z, double N, int tNum, int tSlice) {
        System.out.println("Dispatcher Made");
        myProcessQueue = new ProcessQueue(filename);
        queueCopy=(ArrayList<Input>) PipedDeepCopy.copy(myProcessQueue.Queue);
        this.myMainWindow = z;
        sleepN = N;
        threadNum=tNum;
        timeSlice=tSlice;
        if(threadNum==1) {
            for (int i = 0; i < myProcessQueue.Queue.size(); i++) {//fill queue table
                this.myMainWindow.addRowQueueTable(myProcessQueue.Queue.get(i));
            }
            Thread = new Process(this, HRRN(), sleepN, threadNum, timeSlice);//start execution
            Thread.execute();
        }
        else if(threadNum==2){
            for (int i = 0; i < myProcessQueue.Queue.size(); i++) {//fill queue table
                this.myMainWindow.addRowQueueTable2(myProcessQueue.Queue.get(i));
            }
            Thread = new Process(this, PassProcess(), sleepN, threadNum, timeSlice);//start execution
            Thread.execute();

        }

    }

    public Input RR() {
        ArrayList<Input> possPross=new ArrayList<>();
        for (int i = 0; i < myProcessQueue.Queue.size(); i++) {
            if(myProcessQueue.Queue.get(i).arrivalTime <= this.myMainWindow.time2){
                System.out.println("adding " + myProcessQueue.Queue.get(i).processID + " to possible process array");
                possPross.add(myProcessQueue.Queue.get(i));
            }
        }
        if(possPross.size()==1){
            System.out.println("1 possible process to execute");
            return possPross.get(0);
        }
        else if(possPross.size()>1){
            System.out.println(possPross.size() + " possible processes to execute");
            float hrr = -9999;
            float temp;
            int loc=-1;
            for (int i = 0; i < possPross.size(); i++){
                temp=((this.myMainWindow.time1-possPross.get(i).arrivalTime)+possPross.get(i).serviceTime)/possPross.get(i).serviceTime;
                if(hrr<temp){
                    hrr=temp;
                    loc=i;
                }
            }
            return possPross.get(loc);
        }
        else{
            System.out.println("no possible process to execute");
            return null;
        }
    }

     public Input HRRN() {
         ArrayList<Input> possPross=new ArrayList<>();
         for (int i = 0; i < myProcessQueue.Queue.size(); i++) {
             if(myProcessQueue.Queue.get(i).arrivalTime <= this.myMainWindow.time1){
                 System.out.println("adding " + myProcessQueue.Queue.get(i).processID + " to possible process array");
                 possPross.add(myProcessQueue.Queue.get(i));
             }
         }
         if(possPross.size()==1){
             System.out.println("1 possible process to execute");
             return possPross.get(0);
         }
         else if(possPross.size()>1){
             System.out.println(possPross.size() + " possible processes to execute");
             float hrr = -9999;
             float temp;
             int loc=-1;
             for (int i = 0; i < possPross.size(); i++){
                 temp=((this.myMainWindow.time1-possPross.get(i).arrivalTime)+possPross.get(i).serviceTime)/possPross.get(i).serviceTime;
                 if(hrr<temp){
                     hrr=temp;
                     loc=i;
                 }
             }

             return possPross.get(loc);
         }
         else{
             System.out.println("no possible process to execute");
             return null;
         }
     }


    public Input PassProcess() {//pass current process
        return myProcessQueue.Queue.get(0)
                ;
    }

    public void RemoveProcess(int i) {//remove current process
        myProcessQueue.Queue.remove(i);
    }

    private void ThreadDone() {//when thread1 done, update throughtput and do next process if not empty
        if(threadNum==1) {
            System.out.println("Thread1 Done");
            this.myMainWindow.updateThroughput();
        }
        else if(threadNum==2) {
            System.out.println("Thread2 Done");
            this.myMainWindow.updateThroughput2();
        }
        //this.myMainWindow.updateThroughput2();
        if (!myProcessQueue.Queue.isEmpty()) {
            if(threadNum==1) {
                Thread = new Process(this, HRRN(), sleepN, threadNum, timeSlice);
                Thread.execute();
            }
            else if(threadNum==2) {
                Thread = new Process(this, PassProcess(), sleepN, threadNum, timeSlice);
                Thread.execute();
            }
        } else {//else done with thread and update GUI
            if(threadNum==1) {
                System.out.println("Thread1 Complete");
                this.myMainWindow.setExecStatus1("Finished");
                this.myMainWindow.timer1.cancel();
                this.myMainWindow.timeLabel1.setText("time remaining: NaN");
            }
            else if(threadNum==2){
                System.out.println("Thread2 Complete");
                this.myMainWindow.setExecStatus2("Finished");
                this.myMainWindow.timer2.cancel();
                this.myMainWindow.timeLabel2.setText("time remaining: NaN");
            }
        }

    }

    public void ThreadBefore(Input currentProcess) {//update GUI before simulated process

        System.out.println("Thread Before");
        for (int i = 0; i < myProcessQueue.Queue.size(); i++) {
            if(currentProcess.processID == myProcessQueue.Queue.get(i).processID){
                RemoveProcess(i);
                if(threadNum==1) {
                    this.myMainWindow.setExecStatus1(currentProcess.processID);
                    this.myMainWindow.setTimeLabel1(currentProcess.serviceTime, sleepN);
                    this.myMainWindow.removeRowQueueTable(i);
                }
                else if(threadNum==2) {
                    this.myMainWindow.setExecStatus2(currentProcess.processID);
                    this.myMainWindow.setTimeLabel2(currentProcess.serviceTime, sleepN);
                    this.myMainWindow.removeRowQueueTable2(i);
                }
            }

        }

    }

    public void ThreadAfter(Input currentProcess) {//update GUI after simulated process
        System.out.println("Thread After");
        System.out.println(currentProcess.processID);
        if(threadNum==1) {
            this.myMainWindow.addRowProcessInfoTable(currentProcess); //table 1
        }
        else if(threadNum==2) {
            currentProcess.serviceTime= currentProcess.serviceTime-timeSlice;
            if(currentProcess.serviceTime<=0) {
                System.out.println("Service time for current process <=0");
                for(int i=0;i<queueCopy.size();i++){
                    System.out.println(queueCopy.get(i).serviceTime);
                    if(currentProcess.processID.equals(queueCopy.get(i).processID)){
                        System.out.println("adding " + queueCopy.get(i).processID + " to process info table 2");
                        this.myMainWindow.addRowProcessInfoTable2(queueCopy.get(i)); //table 2
                    }
                }

            }
            else if(currentProcess.serviceTime>0){
                this.myMainWindow.addRowQueueTable2(currentProcess);
                myProcessQueue.Queue.add(currentProcess);
            }
        }
        ThreadDone();
    }

}