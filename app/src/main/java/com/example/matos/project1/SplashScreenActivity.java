package com.example.matos.project1;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreenActivity extends AppCompatActivity {

    private int SPLASH_TIMEOUT = 3000;

    LottieAnimationView lottieAnimnationsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        // Make the change from MenuActivity to HomeActivity after 1.5 sec (SPLASH_TIMEOUT)
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent HomeIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(HomeIntent);
                finish();
            }
        },SPLASH_TIMEOUT);

    }


}



