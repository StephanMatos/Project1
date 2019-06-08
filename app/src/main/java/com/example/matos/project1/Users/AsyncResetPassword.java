package com.example.matos.project1.Users;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncResetPassword extends AsyncTask<String,Void,Boolean> {
    @Override
    protected Boolean doInBackground(String... strings) {
        String data;

        try {

            String LoginUrl = "https://easyeats.dk/resetPassword.php?email="+strings[0];
            URL url = new URL(LoginUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            data = bufferedReader.readLine();
            String response = connection.getResponseMessage();
            System.out.println("Message is :" + data);
            System.out.println("Response is : "+ response);

            if(data.equals("success")){
                return true;
            }

        }catch (IOException e){
            e.printStackTrace();
        }


        return false;
    }
}
