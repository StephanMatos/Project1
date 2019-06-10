package com.example.matos.project1.Users;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.example.matos.project1.AlertDialogBoxes;
import com.example.matos.project1.R;

import dmax.dialog.SpotsDialog;



public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email_EditText, editTextCode1, editTextCode2, editTextCode3, editTextCode4, editTextCode5;
    private Button send_Button;
    private Context context;
    public static AlertDialog progressDialog;
    public static boolean success = false;
    public static boolean failure = false;
    public static boolean network = false;
    private boolean active;


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

        new AsyncResetPassword().execute(email_EditText.getText().toString());
        waitForResults();
    }

    void resetDialog(){
        final Dialog dialog = new Dialog(ForgotPasswordActivity.this);

        dialog.setContentView(R.layout.dialogview_passreset);
        dialog.setTitle("verification code");

        editTextCode1 = dialog.findViewById(R.id.editTextCode1);
        editTextCode1.requestFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        dialog.show();

    }

    void waitForResults(){
        active = true;
        new Thread(new Runnable() {
            public void run() {
                while(active){
                    try {
                        if(success){
                            progressDialog.dismiss();
                            resetDialog();
                            active = false;
                        }else if(failure){

                            System.out.println("dialog");
                            progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUIContext("Fejl","Den indtastede email findes ikke i systemet. Tjek venligst den indstastede email",getContext());
                            active = false;
                        }else if(network){
                            progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUIContext("Fejl","Kontroller at telefonen har forbindelse til internettet",getContext());
                            active = false;

                        }
                        Thread.sleep(200);
                    } catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }).start();
    }
}
