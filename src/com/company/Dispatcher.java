package com.company;

/*
* Class Dispatcher
* makes Process Queue
* makes threads
* provides update functions for the GUI via MainWindow instance
* continue doing processes until no more
*/
public class Dispatcher{

    MainWindow myMainWindow;// MainWindow instance
    protected ProcessQueue myProcessQueue;//singleton process queue

    double sleepN;//time unit
    private Process Thread1;//first thread
    private Process Thread2;//second thread

    public Dispatcher(String filename, MainWindow z, double N) {
        System.out.println("Dispatcher Made");
        myProcessQueue = ProcessQueue.getInstance(filename);
        this.myMainWindow=z;
        sleepN=N;
        for (int i = 0; i < myProcessQueue.Queue.size(); i++){//fill queue table
            this.myMainWindow.addRowQueueTable(myProcessQueue.Queue.get(i));
        }
        //start thread execution
        Thread1=new Process(this, PassProcess(), sleepN, 1);
        RemoveLast();
        Thread2=new Process(this, PassProcess(), sleepN, 2);
        RemoveLast();
        Thread1.execute();
        Thread2.execute();



    }


    public Input PassProcess(){//pass current process
        return myProcessQueue.Queue.get(0);
    }

    public void RemoveLast(){//remove current process
        myProcessQueue.Queue.remove(0);
    }

    private void Thread1Done(){//when thread1 done, update throughtput and do next process if not empty
        System.out.println("Thread1 Done");
        this.myMainWindow.updateThroughput();
        if(!myProcessQueue.Queue.isEmpty()) {
            Thread1=new Process(this, PassProcess(), sleepN, 1);
            RemoveLast();
            Thread1.execute();
        }
        else{//else done with thread and update GUI
            System.out.println("Thread1 Complete");
            this.myMainWindow.setExecStatus1("Finished");
            this.myMainWindow.timer1.cancel();
            this.myMainWindow.timeLabel1.setText("time remaining: 0");
        }

    }

    private void Thread2Done(){//when thread2 done, update throughtput and do next process if not empty
        System.out.println("Thread2 Done");
        this.myMainWindow.updateThroughput();
        if(!myProcessQueue.Queue.isEmpty()) {
            Thread2=new Process(this, PassProcess(), sleepN, 2);
            RemoveLast();
            Thread2.execute();
        }
        else{//else done with thread and update GUI
            System.out.println("Thread2 Complete");
            this.myMainWindow.setExecStatus2("Finished");
            this.myMainWindow.timer2.cancel();
            this.myMainWindow.timeLabel2.setText("time remaining: 0");
        }
    }

    public void Thread1Before(Input currentProcess){//update GUI before simulated process

            System.out.println("Thread1 Before");
            this.myMainWindow.setExecStatus1(currentProcess.processID);
            this.myMainWindow.setTimeLabel1(currentProcess.serviceTime, sleepN);
            this.myMainWindow.removeRowQueueTable(0);

    }

    public void Thread1After(Input currentProcess){//update GUI after simulated process
        System.out.println("Thread1 After");
        System.out.println(currentProcess.processID);
        this.myMainWindow.addRowProcessInfoTable(currentProcess);
        Thread1Done();
    }

    public void Thread2Before(Input currentProcess){//update GUI before simulated process
        System.out.println("Thread2 Before");
        this.myMainWindow.setExecStatus2(currentProcess.processID);
        this.myMainWindow.setTimeLabel2(currentProcess.serviceTime, sleepN);
        this.myMainWindow.removeRowQueueTable(0);

    }

    public void Thread2After(Input currentProcess){//update GUI after simulated process
        System.out.println("Thread2 After");
        System.out.println(currentProcess.processID);
        this.myMainWindow.addRowProcessInfoTable(currentProcess);
        Thread2Done();
    }

}
