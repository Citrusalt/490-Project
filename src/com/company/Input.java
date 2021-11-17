package com.company;

/*
* Class Input
* Provides object for passing file contents
* one Input object represents one process
*/

import java.io.Serializable;

public class Input implements Serializable {
        int arrivalTime;
        String processID;
        int serviceTime;
        int priority;
        public Input(){
                int arrivalTime=0;
                String processID=null;
                int serviceTime=0;
                int priority=0;
        }


}

