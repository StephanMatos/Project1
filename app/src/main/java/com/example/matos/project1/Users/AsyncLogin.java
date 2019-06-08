package com.example.matos.project1.Users;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.CheckBox;

import com.example.matos.project1.Menu.HomeActivity;
import com.example.matos.project1.SavedValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class AsyncLogin extends AsyncTask<String,Void,Boolean> {
    private Context context;

    private String email,password;
    SavedValues savedValues = SavedValues.getInstance();


    public AsyncLogin(Context context){
        this.context = context;
    }


    @Override
    protected Boolean doInBackground(String... Strings) {
        boolean success = false;
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
            System.out.println("Message is :" + data);
            System.out.println("Response is : "+ response);

            connection.disconnect();
            if(data.equals("success")){
                success = true;
            }else{
                success = false;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return success;
    }

    @Override
    protected void onPostExecute(Boolean success){
        if(success){
            TabLoginFragment.progressDialog.dismiss();
            Intent intent = new Intent(context,HomeActivity.class);
            context.startActivity(intent);
            ((Activity)context).finish();
            savedValues.saveEmail(email);
            savedValues.savePassword(password);



        } else{
            //publish faulty dialog
            TabLoginFragment.progressDialog.dismiss();
            new AlertDialog.Builder(context)
                    .setTitle("Wrong Email or Password")
                    .setMessage("Username can not be used to login")
                    .setNeutralButton("OK",null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}