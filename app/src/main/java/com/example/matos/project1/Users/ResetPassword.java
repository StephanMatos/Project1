package com.example.matos.project1.Users;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.matos.project1.AlertDialogBoxes;
import com.example.matos.project1.AsyncTask.AsyncResetPassword;
import com.example.matos.project1.R;
import com.example.matos.project1.ResultThread;

import dmax.dialog.SpotsDialog;

public class ResetPassword extends AppCompatActivity {

    private EditText newPassword_EditText, verifyPassword_EditText;
    private Button savePass_Button;
    private String email;
    public static AlertDialog progressDialog;
    public static boolean success = false;
    public static boolean failure = false;
    public static boolean network = false;
    public static boolean active = true;
    public static boolean unknown = false;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        context = this;

        if(bundle!=null) {
            email =(String) bundle.get("email");
            System.out.println("This is email : "+email);
        }

        newPassword_EditText = findViewById(R.id.newPassword_EditText);
        verifyPassword_EditText = findViewById(R.id.verifyPassword_EditText);
        savePass_Button = findViewById(R.id.savePass_Button);

        savePass_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPasswords();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialogBoxes.cancelPasswordReset(getContext(),"Advarsel","Hvis du trykker ok vil du blive sendt tilbage til login skærmen uden at have ændret dit password");
    }

    public static void resetBooleans(){
        success = false;
        failure = false;
        network = false;
        active = true;
        unknown = false;
    }

    Context getContext(){
        return context;
    }


    public void checkPasswords(){
        String password1 = newPassword_EditText.getText().toString();
        String password2 = verifyPassword_EditText.getText().toString();

        boolean pass1 = CheckValues.checkPassword(password1);
        boolean pass2 = CheckValues.checkPassword(password2);

        if(!pass1 && !pass2){
            AlertDialogBoxes.alertDialogOnUIContext("Fejl","Det indtastede kodeord opfylder ikke kravene",getContext());
        }else if(!password1.equals(password2)){
            AlertDialogBoxes.alertDialogOnUIContext("Fejl","De indtastede kodeord stemmer ikke overens",getContext());

        } else {

            new AsyncResetPassword().execute(email,password1);
            progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(getContext()).build();
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            ResultThread.waitForResults(false,getParent(),context,email,password1,false);

        }
    }

}
