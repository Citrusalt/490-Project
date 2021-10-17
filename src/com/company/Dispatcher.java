package com.company;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Dispatcher {

    protected ArrayList<Input> DispatcherQueue;
    public int sizeQueue;

    Dispatcher(ArrayList<Input> inputAry) {
        System.out.println("Dispatcher Made");
       DispatcherQueue = inputAry;
       sizeQueue=DispatcherQueue.size();
    }

    public Input PassProcess(){
        return DispatcherQueue.get(0);
    }

    public void RemoveLast(){
        DispatcherQueue.remove(0);
    }

}
