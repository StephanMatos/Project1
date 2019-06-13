package com.example.matos.project1.Users;

import android.app.AlertDialog;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class AsyncRequestResetPassword extends AsyncTask<String,Void,Void> {

    @Override
    protected Void doInBackground(String... Strings) {
        System.out.println("AsyncRequestResetPassword");
        ForgotPasswordActivity.setBooleans();

        try {

            String resetUrl = "https://easyeats.dk/requestResetPassword.php?email="+Strings[0];
            System.out.println(resetUrl);

            URL url = new URL(resetUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String data = bufferedReader.readLine();
            String response = connection.getResponseMessage();
            connection.disconnect();

            System.out.println("This is Response : "+response);
            System.out.println("This is data : "+data);

            if(response.equals("OK")){
                if(data.equals("success")){
                    ForgotPasswordActivity.success = true;
                }else{
                    ForgotPasswordActivity.failure = true;
                }
            }else{
                ForgotPasswordActivity.network = true;
            }


        }catch (IOException | NullPointerException e){
            e.printStackTrace();
        }

        return null;
    }



}
