package com.company;
import java.util.ArrayList;

public class ProcessQueue {
    private static ProcessQueue x=null;
    public ArrayList<Input> Queue;

    private ProcessQueue(String filename){
        System.out.println("ProcessQueue Made");
        CSVReader myCSVReader = new CSVReader(filename);
        Queue = myCSVReader.createArray();
    }
    public static ProcessQueue getInstance(String filename){
        if(x==null)
            x=new ProcessQueue(filename);
        return x;
    }

}
