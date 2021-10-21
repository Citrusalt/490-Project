package com.company;


public class Dispatcher{

    MainWindow myMainWIndow;
    protected ProcessQueue myProcessQueue;

    double sleepN;
    private Process Thread1;
    private Process Thread2;

    public Dispatcher(String filename, MainWindow z, double N) {
        System.out.println("Dispatcher Made");
        myProcessQueue = ProcessQueue.getInstance(filename);
        this.myMainWIndow=z;
        sleepN=N;
        for (int i = 0; i < myProcessQueue.Queue.size(); i++){
            this.myMainWIndow.addRowQueueTable(myProcessQueue.Queue.get(i));
        }
        Thread1=new Process(this, PassProcess(), sleepN, 1);
        RemoveLast();
        Thread2=new Process(this, PassProcess(), sleepN, 2);
        RemoveLast();
        Thread1.execute();
        Thread2.execute();



    }


    public Input PassProcess(){
        return myProcessQueue.Queue.get(0);
    }

    public void RemoveLast(){
        myProcessQueue.Queue.remove(0);
    }

    private void Thread1Done(){
        System.out.println("Thread1 Done");
        this.myMainWIndow.updateThroughput();
        if(!myProcessQueue.Queue.isEmpty()) {
            Thread1=new Process(this, PassProcess(), sleepN, 1);
            RemoveLast();
            Thread1.execute();
        }
        else{
            System.out.println("Thread1 Complete");
            this.myMainWIndow.setExecStatus1("Finished");
            this.myMainWIndow.timeLabel1.setText("time remaining: 0");
        }

    }

    private void Thread2Done(){
        System.out.println("Thread2 Done");
        this.myMainWIndow.updateThroughput();
        if(!myProcessQueue.Queue.isEmpty()) {
            Thread2=new Process(this, PassProcess(), sleepN, 2);
            RemoveLast();
            Thread2.execute();
        }
        else{
            System.out.println("Thread2 Complete");
            this.myMainWIndow.setExecStatus2("Finished");
            this.myMainWIndow.timeLabel2.setText("time remaining: 0");
        }
    }

    public void Thread1Before(Input currentProcess){

            System.out.println("Thread1 Before");
            this.myMainWIndow.setExecStatus1(currentProcess.processID);
            this.myMainWIndow.setTimeLabel1(currentProcess.serviceTime, sleepN);
            this.myMainWIndow.removeRowQueueTable(0);

    }

    public void Thread1After(Input currentProcess){
        System.out.println("Thread1 After");
        System.out.println(currentProcess.processID);
        this.myMainWIndow.addRowProcessInfoTable(currentProcess);
        Thread1Done();
    }

    public void Thread2Before(Input currentProcess){
        System.out.println("Thread2 Before");
        this.myMainWIndow.setExecStatus2(currentProcess.processID);
        this.myMainWIndow.setTimeLabel2(currentProcess.serviceTime, sleepN);
        this.myMainWIndow.removeRowQueueTable(0);

    }

    public void Thread2After(Input currentProcess){
        System.out.println("Thread2 After");
        System.out.println(currentProcess.processID);
        this.myMainWIndow.addRowProcessInfoTable(currentProcess);
        Thread2Done();
    }

}
