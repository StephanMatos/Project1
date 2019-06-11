package com.example.matos.project1.Users;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class AsyncCheckVerification extends AsyncTask<String,Void,Void> {
    // hello
    @Override
    protected Void doInBackground(String... Strings) {
        ForgotPasswordActivity.setBooleans();
        String email = Strings[0];
        String code = Strings[1];

        try {
            String resetUrl = "https://easyeats.dk/checkVerification.php?email="+email+"&verification="+code;
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
                    ForgotPasswordActivity.verification = true;
                }else{
                    ForgotPasswordActivity.verificationError = true;
                }
            }else{
                ForgotPasswordActivity.network = true;
            }


        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }



}