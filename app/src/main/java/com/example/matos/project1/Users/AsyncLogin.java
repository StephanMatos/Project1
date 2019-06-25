package com.example.matos.project1.Users;

import android.os.AsyncTask;
import com.example.matos.project1.SavedValues;
import com.example.matos.project1.SplashScreenActivity;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class AsyncLogin extends AsyncTask<String,Void,Void> {


    private String email,password;
    private SavedValues savedValues = SavedValues.getInstance();


    @Override
    protected Void doInBackground(String... Strings) {
        TabLoginFragment.setBooleans();
        SplashScreenActivity.setBooleans();


        String data;
        email = Strings[0];
        password = Strings[1];

        try {
            String LoginUrl = "https://easyeats.dk/EasyEats/login.php?";
            System.out.println(LoginUrl);

            URL url = new URL(LoginUrl);



            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept","application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            JSONObject jsonLogin = new JSONObject();
            jsonLogin.put("email",email);
            jsonLogin.put("password",password);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(jsonLogin.toString());
            outputStream.flush();
            outputStream.close();


            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            data = bufferedReader.readLine();
            String response = connection.getResponseMessage();
            connection.disconnect();

            System.out.println("This is Response : "+response);
            System.out.println("This is data : "+data);

            if(data == null || response == null){
                System.out.println("NullPointerException");
                TabLoginFragment.network = true;
            }else{
                if(response.equals("OK")){
                    if(data.equals("success")){
                        TabLoginFragment.success = true;
                        SplashScreenActivity.success = true;
                        savedValues.saveEmail(email);
                        savedValues.savePassword(password);
                    }else{
                        System.out.println("inside failure");
                        TabLoginFragment.failure = true;
                        SplashScreenActivity.failure = true;
                    }
                }else{
                    TabLoginFragment.network = true;
                }
            }


        }catch (IOException | NullPointerException | JSONException e){
            TabLoginFragment.unknown = true;

            e.printStackTrace();
        }

        return null;
    }
}
