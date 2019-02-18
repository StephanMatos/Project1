package com.example.matos.project1;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Services {


    public static void saveAppendTo(Context context, String str, String filename){



        ArrayList<String> ids = loadIdsFrom(context, filename);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename+".txt", Context.MODE_PRIVATE));

            for (String i : ids){
                outputStreamWriter.write("#" + i);
            }
            outputStreamWriter.write("#" + str);

            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> loadIdsFrom(Context context, String filename) {

        String str = "";

        try {
            InputStream inputStream = context.openFileInput(filename+".txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                str = stringBuilder.toString();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String[] strs = {};

        if(str.equals(""))
            return new ArrayList<String>();

        strs = str.split("#");

        ArrayList<String> result = new ArrayList<>(Arrays.asList(strs));
        result.remove(0);

        return result;
    }

    public static void deleteIdFrom(Context context, String id, String filename){

        ArrayList<String> ids = loadIdsFrom(context, filename);

        ids.remove(id);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename+".txt", Context.MODE_PRIVATE));

            for (String i : ids){
                outputStreamWriter.write("#" + i);
            }

            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}