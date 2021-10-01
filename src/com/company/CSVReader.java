package com.company;
/*
Phase I of the CS 490 Group project
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;


public class CSVReader {

    public ArrayList<Input> createArray(String csv_filename){
        //have the user input a file name

        File csv = new File(csv_filename);

        //parse the csv file
        String line = "";
        String delimiter = ",";

        ArrayList<Input> dataAry = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            while (br.ready())
            {
                Input data = new Input();
                String[] ary = br.readLine().split(",");
                data.arrivalTime=Integer.parseInt(ary[0]);
                data.processID=ary[1];
                data.serviceTime=Integer.parseInt(ary[2]);
                data.priority=Integer.parseInt(ary[2]);

                dataAry.add(data);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //System.out.println(Arrays.deepToString(data.toArray()));
        System.out.println(dataAry);
        return dataAry;

    }
}