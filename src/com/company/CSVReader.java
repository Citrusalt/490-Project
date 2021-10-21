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

/*
*  Class CSVReader
*  read file and return contents as Input objects
*/
public class CSVReader {

    private final String csv_filename;//name of csv file

    CSVReader(String fileName){
        csv_filename=fileName;//set file name
    }


    public ArrayList<Input> createArray(){
        //have the user input a file name

        File csv = new File(csv_filename);

        //parse the csv file
        String line = "";
        String delimiter = ",";

        ArrayList<Input> dataAry = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            while (br.ready())//assign contents to Input objects
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

        return dataAry;//return Input objects

    }
}
