package com.company;

public interface GUIInterface {
        void updateThroughput();
        void removeRowQueueTable(int i);
        void addRowProcessInfoTable(Input I);
        void setTimeLabel1(int servicetime, double sleepN);
        void setTimeLabel2(int servicetime, double sleepN);
        void setExecStatus1(String processID);
        void setExecStatus2(String processID);
        void addRowQueueTable(Input myInput);
}
