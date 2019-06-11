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
        String email = Strings[0];
        String code = Strings[1];
        String password1 = Strings[2];
        String password2 = Strings[3];
        try {
//hello
            String resetUrl = "https://easyeats.dk/resetPassword.php?email="+email+"code="+code+"password1="+password1+"password2="+password2;

            URL url = new URL(resetUrl);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String data = bufferedReader.readLine();

            String response = connection.getResponseMessage();

            connection.disconnect();

            System.out.println(response);

            if(response.equals("OK")){
                if(data.equals("success")){

                }else{

                }
            }else{

            }


        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }



}
