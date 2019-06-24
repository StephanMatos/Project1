package com.example.matos.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

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


        try {

            query = query.replaceAll(" ", "%20");
            URL url = new URL("https://easyeats.dk/EasyEats/" + query);
            System.out.println(url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            bufferedReader.close();
            urlConnection.disconnect();

            //System.out.println("data:" + stringBuilder.toString());
            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void postAPI(String address) {

        try {

            address = address.replaceAll(" ", "%20");
            URL url = new URL("https://easyeats.dk/EasyEats/" + address);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setDoOutput(true);

            int responseCode = urlConnection.getResponseCode();
            System.out.println("Response code is------------------ " + responseCode);
            urlConnection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void postAPI(String address, Bitmap image) {

        System.out.println("Calling post API");
        try {

            address = address.replaceAll(" ", "%20");
            URL url = new URL("https://easyeats.dk/EasyEats/" + address);
            System.out.println("URL is: " + url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setDoOutput(true);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            //writer.write("image=" + getStringImage(image) + "&imagename=" + filename);
            writer.write("image=" + getStringImage(image));
            //System.out.println(getStringImage(image));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = urlConnection.getResponseCode();
            System.out.println("Response code is------------------ " + responseCode);
            String response = "";
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line = br.readLine();
                response = line;
                while(line != null){
                    line = br.readLine();
                    response += line;
                }
            } else {
                response = "Error Registering";
            }
            System.out.println("response is: " + response);
            urlConnection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap StringToBitMap(String encodedString){
        //encodedString = encodedString.replace("\\/", "/");
        //encodedString = encodedString.replace(" ", "+");
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public static ArrayList<JSONObject> testJSONS(){

        ArrayList<JSONObject> jsons = new ArrayList<JSONObject>();

        for(int i = 1; i < 20; i++){
            JSONObject json = new JSONObject();
            try {
                json.put("barcode", "" + i);
                json.put("name", "Test product " + (i));
                json.put("state", "frozen");

                double r = Math.random()*5;
                json.put("rating", r);

                if(r > 3.5){
                    json.put("inFavorite", true);
                } else{
                    json.put("inFavorite", false);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsons.add(json);
        }

        return jsons;

    }

    public static JSONObject testProductJSON(String barcode){

        ArrayList<JSONObject> jsons = testJSONS();

        for(JSONObject json : jsons){
            try {
                if(json.getString("barcode").equals(barcode)){
                    return json;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
