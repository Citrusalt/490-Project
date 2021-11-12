package com.company;
import java.util.ArrayList;

/*
* Class ProcessQueue
* singleton queue on Input objects
*/

public class ProcessQueue {
    public ArrayList<Input> Queue;
    ProcessQueue(String filename){
        System.out.println("ProcessQueue Made");
        CSVReader myCSVReader = new CSVReader(filename);
        Queue = myCSVReader.createArray();
    }

}
