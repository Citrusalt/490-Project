package com.company;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Dispatcher {

    protected ProcessQueue myProcessQueue;
    public int sizeQueue;

    Dispatcher(String filename) {
        System.out.println("Dispatcher Made");
        myProcessQueue = ProcessQueue.getInstance(filename);
        sizeQueue=myProcessQueue.Queue.size();
    }

    public Input PassProcess(){
        return myProcessQueue.Queue.get(0);
    }

    public void RemoveLast(){
        myProcessQueue.Queue.remove(0);
    }

}
