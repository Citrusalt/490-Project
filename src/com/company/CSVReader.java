package com.company;
/*
Phase I of the CS 490 Group project
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;


public class CSVReader {

    public void createArray(String csv_filename) {
        //have the user input a file name

        File csv = new File(csv_filename);

        //parse the csv file
        String line = "";
        String delimiter = ",";

        ArrayList<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            while (br.ready())
            {
                data.add(br.readLine());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //System.out.println(Arrays.deepToString(data.toArray()));
        System.out.println(data.get(1));

    }
}
