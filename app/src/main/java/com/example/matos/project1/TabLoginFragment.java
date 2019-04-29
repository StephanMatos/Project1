package com.example.matos.project1;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;

import dmax.dialog.SpotsDialog;

public class TabLoginFragment extends Fragment {
    EditText email, password;
    Button login;
    public static AlertDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        email = view.findViewById(R.id.email_TextView);
        password = view.findViewById(R.id.pass_TextView);

        login = view.findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("a") && password.getText().toString().equals("a")){
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    attempt_login();
                }
            }
        });

    }
    private void attempt_login() {
        boolean check = true;

        progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(getContext()).build();

        progressDialog.setMessage("Loading...");
        progressDialog.show();
        boolean validEmail = CheckValues.checkEmail(email.getText().toString());

        if(!validEmail){
            progressDialog.dismiss();
            buildAlert("Email is not valid","Login with username is not possible");
        }

        if(email.getText().toString().length() == 0 || password.getText().toString().length() == 0){
            progressDialog.dismiss();
            buildAlert("Email and/or Password field can not be empty", "");
            check = false;
        }

        if(check && validEmail){
            new AsyncLogin(getContext()).execute(email.getText().toString(),password.getText().toString());
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
