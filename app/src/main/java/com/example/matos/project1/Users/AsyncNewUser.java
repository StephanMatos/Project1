package com.example.matos.project1.Users;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class AsyncNewUser extends AsyncTask<String,Void,Void> {

    private String email,password,username;

    @Override
    protected Void doInBackground(String... Strings) {
        TabSignupFragment.success = false;
        TabSignupFragment.failure = false;
        TabSignupFragment.exist = false;
        TabSignupFragment.network = false;
        TabLoginFragment.success = false;
        TabLoginFragment.failure = false;
        String data;
        email = Strings[0];
        password = Strings[1];
        username = Strings[2];
        try {

            String LoginUrl = "https://easyeats.dk/signup.php?email="+email+"&password="+password+"&username="+username;
            URL url = new URL(LoginUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            data = bufferedReader.readLine();
            String response = connection.getResponseMessage();

            System.out.println("Message is :" + data);
            System.out.println("Response is : "+ response);
            if(response.equals("OK")){
                if(data.equals("success")){
                    TabSignupFragment.success = true;
                }else if(data.equals("User already exist")){
                    TabSignupFragment.exist = true;
                }else{
                    TabSignupFragment.failure = true;
                }
            }else{

            }


        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}

