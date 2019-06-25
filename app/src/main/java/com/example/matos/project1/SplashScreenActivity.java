package com.example.matos.project1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.matos.project1.AsyncTask.AsyncLogin;
import com.example.matos.project1.Users.LoginActivity;
import com.example.matos.project1.Users.ResultThread;

import static com.example.matos.project1.Users.TabLoginFragment.PREFS_NAME;
import static com.example.matos.project1.Users.TabLoginFragment.mPrefs;

public class SplashScreenActivity extends AppCompatActivity {

    private int SPLASH_TIMEOUT = 500;
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
                    String shared_email = mPrefs.getString("Email","");
                    String shared_password = mPrefs.getString("Password","");
                    prefsEditor.apply();

                    new AsyncLogin().execute(shared_email,shared_password);

                    ResultThread.waitForResults(false,getParent(),getBaseContext(),shared_email,shared_password,true);


                }else{
                    Intent HomeIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    finish();
                    startActivity(HomeIntent);

                }

            }
        },SPLASH_TIMEOUT);

    }

}



