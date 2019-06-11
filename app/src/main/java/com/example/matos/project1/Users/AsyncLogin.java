package com.example.matos.project1.Users;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import com.example.matos.project1.Menu.HomeActivity;
import com.example.matos.project1.SavedValues;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class AsyncLogin extends AsyncTask<String,Void,Void> {


    private String email,password;
    private SavedValues savedValues = SavedValues.getInstance();

    //hello

    @Override
    protected Void doInBackground(String... Strings) {
        TabLoginFragment.setBooleans();

        String data;
        email = Strings[0];
        password = Strings[1];

        try {
            String LoginUrl = "https://easyeats.dk/users.php?email="+Strings[0]+"&password="+Strings[1];
            System.out.println(" This is strings : "+email +  "   "  + password);

            URL url = new URL(LoginUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            data = bufferedReader.readLine();
            String response = connection.getResponseMessage();
            connection.disconnect();

            System.out.println("This is Response : "+response);
            System.out.println("This is data : "+data);

            if(response.equals("OK")){
                if(data.equals("success")){
                    TabLoginFragment.success = true;
                    savedValues.saveEmail(email);
                    savedValues.savePassword(password);
                }else{
                    TabLoginFragment.failure = true;
                }
            }else{
                TabLoginFragment.network = true;
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
