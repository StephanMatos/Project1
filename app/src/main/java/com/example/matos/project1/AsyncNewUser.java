package com.example.matos.project1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class AsyncNewUser extends AsyncTask<String,Void,Boolean> {

    private Context context;
    private String email,password;


    public AsyncNewUser(Context context){
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... Strings) {
        boolean success = false;
        String data;
        email = Strings[0];
        password = Strings[1];

        try {
            String LoginUrl = "https://easyeats.dk/users.php?email="+Strings[0]+"&password="+Strings[1]+"&username"+Strings[2];
            System.out.println(" This is strings : "+Strings[0] +  "   "  + Strings[1]);
            URL url = new URL(LoginUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            data = bufferedReader.readLine();
            String response = connection.getResponseMessage();
            System.out.println("Message is :" + data);
            System.out.println("Response is : "+ response);
            connection.disconnect();



        }catch (IOException e){
            e.printStackTrace();
        }

        return success;
    }

    @Override
    protected void onPostExecute(Boolean success){

        if(success){
        TabSignupFragment.progressDialog.dismiss();
        TabLoginFragment.progressDialog = new ProgressDialog(context);
        TabLoginFragment.progressDialog.setMessage("Loading...");
        TabLoginFragment.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TabLoginFragment.progressDialog.show();
        new AsyncLogin(context).execute(email,password);

        } else{
            //publish faulty dialog

            new AlertDialog.Builder(context)
                    .setTitle("User Already exist")
                    .setMessage("Try logging in")
                    .setNeutralButton("OK",null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}

