package com.example.matos.project1.Users;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.matos.project1.Menu.HomeActivity;
import com.example.matos.project1.R;

import dmax.dialog.SpotsDialog;


public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email_EditText;
    private Button send_Button;
    private Context context;
    public static AlertDialog progressDialog;
    public static boolean success;
    public static boolean failure;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email_EditText = findViewById(R.id.email_EditText);
        send_Button = findViewById(R.id.send_Button);
        context = this;
        send_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });



    }
    Context getContext(){
        return context;
    }


    void resetPassword(){
        progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(this).build();

        progressDialog.setMessage("Loading...");
        progressDialog.show();
        runThread();
        new AsyncResetPassword().execute(email_EditText.getText().toString());
    }

    void runThread(){
        Thread thread = new Thread() {
            boolean running = true;
            @Override
            public void run() {
                try {
                    while(running) {
                        System.out.println("running");
                        if(success){
                            progressDialog.dismiss();
                            running = false;

                            /*
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("Value1", "Success");
                            startActivity(intent);*/

                        } else if (failure){
                            running = false;
                            System.out.println("dialog");
                            progressDialog.dismiss();
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Fejl")
                                    .setMessage("Den indtastede email findes ikke i systemet. Tjek venligst den indstastede email")
                                    .setNeutralButton("OK",null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();


                        }
                        sleep(300);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

    }


}
