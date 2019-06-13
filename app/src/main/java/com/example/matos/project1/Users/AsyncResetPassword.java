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
        System.out.println("AsyncResetPassword");
        String email = Strings[0];
        String password = Strings[1];
        ResetPassword.resetBooleans();



        try {


            String resetUrl = "https://easyeats.dk/resetPassword.php?email="+email+"&password="+password;
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

            if(data == null || response == null) {
                System.out.println("NullPointerException");
                ResetPassword.network = true;
            }else{
                if(response.equals("OK")){
                    if(data.equals("success")){
                        ResetPassword.success = true;
                    }else{
                        ResetPassword.failure = true;
                    }
                }else{
                    ResetPassword.network = true;
                }
            }



        }catch (IOException | NullPointerException e){
            e.printStackTrace();
        }

        return null;
    }



}
