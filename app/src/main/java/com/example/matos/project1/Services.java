package com.example.matos.project1;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Services {


    public static void saveAppendTo(Context context, String str, String filename) {


        ArrayList<String> ids = loadIdsFrom(context, filename);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename + ".txt", Context.MODE_PRIVATE));

            for (String i : ids) {
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
            InputStream inputStream = context.openFileInput(filename + ".txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                str = stringBuilder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] strs = {};

        if (str.equals(""))
            return new ArrayList<String>();

        strs = str.split("#");

        ArrayList<String> result = new ArrayList<>(Arrays.asList(strs));
        result.remove(0);

        return result;
    }

    public static void deleteIdFrom(Context context, String id, String filename) {

        ArrayList<String> ids = loadIdsFrom(context, filename);

        ids.remove(id);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename + ".txt", Context.MODE_PRIVATE));

            for (String i : ids) {
                outputStreamWriter.write("#" + i);
            }

            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String callAPI(String query) {

        System.out.println("API query is: " + query);
        try {

            query = query.replaceAll(" ", "%20");
            //https://schmidt13.azurewebsites.net/test.php?query=
            URL url = new URL("https://schmidt13.azurewebsites.net/test.php?query=" + query);
            //URL url = new URL("http://easyeats.dk/test.php?query=" + query);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            bufferedReader.close();
            urlConnection.disconnect();

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


}
