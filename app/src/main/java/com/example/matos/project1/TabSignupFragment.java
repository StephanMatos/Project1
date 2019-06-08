package com.example.matos.project1;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dmax.dialog.SpotsDialog;

public class TabSignupFragment extends Fragment {
    public static AlertDialog progressDialog;
    private TextView username,email,password;
    private Button signUp;
    boolean validEmail = false;
    boolean validPassword = false;
    boolean validUsername = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        username = view.findViewById(R.id.usernameTextView);
        email = view.findViewById(R.id.email_TextView);
        password = view.findViewById(R.id.pass_TextView);
        signUp = view.findViewById(R.id.login_button);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_user();
            }


        });

    }


    private void create_user() {
        System.out.println("create user");
        progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(getContext()).build();
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        validEmail = CheckValues.checkEmail(email.getText().toString());
        validPassword = CheckValues.checkPassword(password.getText().toString());
        validUsername = CheckValues.checkUsername(username.getText().toString());

        if(!validUsername){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            buildAlert("Error - Username not valid","Username must have a length between 4-20 characters and can only contain a-z A-Z 0-9 <>,;.:_!#¤%&/()=?+*£$€{}" );
        }

        if(!validEmail && validUsername){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            buildAlert("Error - Email not valid","Please enter a valid Email - The entered email will be used to login and recover password if lost");
        }

        if(!validPassword && validEmail && validUsername){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            buildAlert("Error - Password not valid","Password must have a length between 8-20 characters and can only contain a-z A-Z 0-9 <>,;.:_!#¤%&/()=?+*£$€{}");
        }


        if(validEmail && validPassword && validUsername){
            new AsyncNewUser(getContext()).execute(email.getText().toString(),password.getText().toString(),username.getText().toString());

        }


    }

    private void buildAlert(String title, String text){
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(text)
                .setNeutralButton("OK",null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
