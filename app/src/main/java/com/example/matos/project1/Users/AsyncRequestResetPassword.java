package com.example.matos.project1.Users;

import android.app.AlertDialog;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class AsyncRequestResetPassword extends AsyncTask<String,Void,Void> {

    @Override
    protected Void doInBackground(String... Strings) {
        ForgotPasswordActivity.setBooleans();
        String data;
        String email = Strings[0];

        try {

            String resetUrl = "https://easyeats.dk/EasyEats/requestResetPassword.php?";
            System.out.println(resetUrl);

            URL url = new URL(resetUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept","application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            JSONObject jsonLogin = new JSONObject();
            jsonLogin.put("email",email);
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

            if(data == null || response == null) {
                System.out.println("NullPointerException");
                ResetPassword.network = true;
            }else{
                if(response.equals("OK")){
                    if(data.equals("success")){
                        ForgotPasswordActivity.success = true;
                    }else{
                        ForgotPasswordActivity.failure = true;
                    }
                }else{
                    ForgotPasswordActivity.network = true;
                }
            }

        }catch (IOException | NullPointerException | JSONException e){
            ForgotPasswordActivity.unknown = true;
            e.printStackTrace();
        }

        return null;
    }



}
