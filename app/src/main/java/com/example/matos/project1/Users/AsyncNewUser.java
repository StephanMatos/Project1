package com.example.matos.project1.Users;

import android.os.AsyncTask;
import android.widget.Switch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class AsyncNewUser extends AsyncTask<String,Void,Void> {


    //hello
    @Override
    protected Void doInBackground(String... Strings) {
        TabSignupFragment.setBooleans();
        String data;
        String email = Strings[0];
        String password = Strings[1];
        String username = Strings[2];

        try {

            String LoginUrl = "https://easyeats.dk/signup.php?email="+email+"&password="+password+"&username="+username;
            System.out.println(LoginUrl);
            URL url = new URL(LoginUrl);


            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            data = bufferedReader.readLine();
            String response = connection.getResponseMessage();

            System.out.println("This is Response : "+response);
            System.out.println("This is data : "+data);

            if(data == null || response == null) {
                System.out.println("NullPointerException");
                TabSignupFragment.network = true;
            }else{
                if(response.equals("OK")){
                    switch(data){
                        case "success":
                            TabSignupFragment.success = true;
                            break;
                        case "User Already exist":
                            TabSignupFragment.exist = true;
                            break;
                            default:
                                TabSignupFragment.failure = true;
                                break;
                    }
                }else{
                        TabSignupFragment.network = true;
                }
            }

        }catch (IOException | NullPointerException e){
            TabSignupFragment.unknown = true;
            e.printStackTrace();
        }

        return null;
    }
}

