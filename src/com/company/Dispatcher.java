package com.company;

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

    double sleepN;//time unit
    private Process Thread;//first thread
    int threadNum;

    public Dispatcher(String filename, MainWindow z, double N, int tNum) {
        System.out.println("Dispatcher Made");
        myProcessQueue = new ProcessQueue(filename);
        this.myMainWindow = z;
        sleepN = N;
        threadNum=tNum;
        if(threadNum==1) {
            for (int i = 0; i < myProcessQueue.Queue.size(); i++) {//fill queue table
                this.myMainWindow.addRowQueueTable(myProcessQueue.Queue.get(i));
            }
        }
        else if(threadNum==2){
            for (int i = 0; i < myProcessQueue.Queue.size(); i++) {//fill queue table
                this.myMainWindow.addRowQueueTable2(myProcessQueue.Queue.get(i));
            }

        }
        //start thread execution
        Thread = new Process(this, PassProcess(), sleepN, 1);
        RemoveLast();
        Thread.execute();

    }


    public Input PassProcess() {//pass current process
        return myProcessQueue.Queue.get(0)
                ;
    }

    public void RemoveLast() {//remove current process
        myProcessQueue.Queue.remove(0);
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
            Thread = new Process(this, PassProcess(), sleepN, threadNum);
            RemoveLast();
            Thread.execute();
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

        System.out.println("Thread1 Before");

        if(threadNum==1) {
            this.myMainWindow.setExecStatus1(currentProcess.processID);
            this.myMainWindow.setTimeLabel1(currentProcess.serviceTime, sleepN);
            this.myMainWindow.removeRowQueueTable(0);
        }
        else if(threadNum==2) {
            this.myMainWindow.setExecStatus2(currentProcess.processID);
            this.myMainWindow.setTimeLabel2(currentProcess.serviceTime, sleepN);
            this.myMainWindow.removeRowQueueTable2(0);
        }
    }

    public void ThreadAfter(Input currentProcess) {//update GUI after simulated process
        System.out.println("Thread1 After");
        System.out.println(currentProcess.processID);
        if(threadNum==1) {
            this.myMainWindow.addRowProcessInfoTable(currentProcess); //table 1
        }
        else if(threadNum==2) {
            this.myMainWindow.addRowProcessInfoTable2(currentProcess); //table 2
        }
        ThreadDone();
    }
}