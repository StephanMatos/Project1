package com.example.matos.project1.Users;

import android.app.AlertDialog;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class AsyncResetPassword extends AsyncTask<String,Void,Void> {

    @Override
    protected Void doInBackground(String... Strings) {
        ForgotPasswordActivity.success = false;
        ForgotPasswordActivity.failure = false;

        try {

            String resetUrl = "https://easyeats.dk/resetPassword.php?email="+Strings[0];

            URL url = new URL(resetUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String data = bufferedReader.readLine();
            String response = connection.getResponseMessage();
            System.out.println("Message is :" + data);
            System.out.println("Response is : "+ response);

            connection.disconnect();
            if(data.equals("success")){
                ForgotPasswordActivity.success = true;
                System.out.println("succes true");
            }else{
                ForgotPasswordActivity.failure = true;
                System.out.println("failure true");
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }



}
