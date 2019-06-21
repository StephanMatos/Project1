package com.example.matos.project1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.matos.project1.Menu.HomeActivity;
import com.example.matos.project1.Users.AsyncLogin;
import com.example.matos.project1.Users.LoginActivity;

import static com.example.matos.project1.Users.TabLoginFragment.PREFS_NAME;
import static com.example.matos.project1.Users.TabLoginFragment.mPrefs;

public class SplashScreenActivity extends AppCompatActivity {

    private int SPLASH_TIMEOUT = 500;
    public static boolean success;
    public static boolean failure;
    public static boolean network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mPrefs = this.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor = mPrefs.edit();

 
        // Make the change from MenuActivity to HomeActivity after 1.5 sec (SPLASH_TIMEOUT)
        new Handler().postDelayed(new Runnable() {

           @Override
            public void run() {
                if (mPrefs.getBoolean("CheckBox",false)){
                    System.out.println("auto");
                    String shared_email = mPrefs.getString("Email","");
                    System.out.println(shared_email);
                    String shared_password = mPrefs.getString("Password","");
                    System.out.println(shared_password);
                    prefsEditor.apply();

                    new AsyncLogin().execute(shared_email,shared_password);
                    waitForResults(getBaseContext(),SplashScreenActivity.this);


                }else{
                    System.out.println("not auto");
                    Intent HomeIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    finish();
                    startActivity(HomeIntent);

                }

            }
        },SPLASH_TIMEOUT);

    }

    static void waitForResults(final Context context,final SplashScreenActivity splash){

        new Thread(new Runnable() {
            public void run() {

                boolean active = true;

                while(active){
                    try {
                        if(success){
                            Intent intent = new Intent(context,HomeActivity.class);
                            context.startActivity(intent);
                            active = false;
                            splash.finish();
                        }else if(failure){
                            Intent intent = new Intent(context,LoginActivity.class);
                            context.startActivity(intent);
                            context.getSharedPreferences("CheckboxFile",MODE_PRIVATE).edit().clear().apply();
                            active = false;
                        }else if(network){
                            active = false;
                            Intent intent = new Intent(context,LoginActivity.class);
                            context.startActivity(intent);
                        }

                        Thread.sleep(200);
                    } catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }).start();

    }


    public static void setBooleans(){
        success = false;
        failure = false;
        network = false;
    }


}



