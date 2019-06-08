package com.example.matos.project1;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.matos.project1.Users.AsyncLogin;
import com.example.matos.project1.Users.AsyncResetPassword;
import com.example.matos.project1.Users.CheckValues;

import dmax.dialog.SpotsDialog;

import static com.example.matos.project1.TabSignupFragment.progressDialog;
import static java.security.AccessController.getContext;

public class ForgotPasswordActivity extends AppCompatActivity {

    public static AlertDialog progressDialog;
    private EditText email_EditText;
    private Button send_Button;
    private boolean validEmail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_EditText = findViewById(R.id.email_EditText);
        send_Button = findViewById(R.id.send_Button);

        send_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempt_passRecovery(email_EditText.getText().toString());
            }
        });


    }

    public void attempt_passRecovery(String email){

        progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(this).build();

        progressDialog.setMessage("Loading...");
        progressDialog.show();


        validEmail = CheckValues.checkEmail(email);

        if(!validEmail){
            progressDialog.dismiss();
            buildAlert("Email is not valid","Login with username is not possible");
        }
    //JUHUUUUU
        if (validEmail){
            progressDialog.dismiss();


        }



    }

    private void buildAlert(String title, String text){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(text)
                .setNeutralButton("OK",null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
