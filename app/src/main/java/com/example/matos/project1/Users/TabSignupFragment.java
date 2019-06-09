package com.example.matos.project1.Users;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.matos.project1.R;

public class TabSignupFragment extends Fragment {
    public static ProgressDialog progressDialog;
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
        email = view.findViewById(R.id.email_EditText);
        password = view.findViewById(R.id.pass_TextView);
        signUp = view.findViewById(R.id.send_Button);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_user();
            }


        });
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validUsername = CheckValues.checkUsername(username.getText().toString());
                if(validUsername){
                    System.out.println("valid");
                    username.setTextColor(Color.GREEN);
                }else{
                    username.setTextColor(Color.RED);
                    System.out.println("not valid");
                }

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validEmail = CheckValues.checkEmail(email.getText().toString());

                if(validEmail){
                    System.out.println("valid");
                    email.setTextColor(Color.GREEN);
                }else{
                    email.setTextColor(Color.RED);
                    System.out.println("not valid");
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validPassword = CheckValues.checkPassword(password.getText().toString());
                if(validPassword){
                    System.out.println("valid");
                    password.setTextColor(Color.GREEN);
                }else{
                    password.setTextColor(Color.RED);
                    System.out.println("not valid");
                }

            }
        });

    }


    private void create_user() {
        System.out.println("create user");
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        validEmail = CheckValues.checkEmail(email.getText().toString());
        validPassword = CheckValues.checkPassword(password.getText().toString());
        validUsername = CheckValues.checkUsername(username.getText().toString());


        //buildAlert("Error - Username not valid","Username must have a length between 4-20 characters and can only contain a-z A-Z 0-9 <>,;.:_!#¤%&/()=?+*£$€{}" );
        //buildAlert("Error - Email not valid","Please enter a valid Email - The entered email will be used to login and recover password if lost");
        //buildAlert("Error - Password not valid","Password must have a length between 8-20 characters and can only contain a-z A-Z 0-9 <>,;.:_!#¤%&/()=?+*£$€{}");


        if(validEmail && validPassword && validUsername){
            new AsyncNewUser(getContext()).execute(email.getText().toString(),password.getText().toString(),username.getText().toString());

        } else{
            buildAlert("Fejl40 - Alle felter skal være grønne ","Tryk på spørgsmålestegnet ved tvivl" );
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
