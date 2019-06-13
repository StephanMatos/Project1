package com.example.matos.project1.Users;


import android.app.AlertDialog;
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
import android.widget.Toast;

import com.example.matos.project1.AlertDialogBoxes;
import com.example.matos.project1.R;

import dmax.dialog.SpotsDialog;

public class TabSignupFragment extends Fragment {

    // Validation of user input
    boolean validEmail = false;
    boolean validPassword = false;
    boolean validUsername = false;

    public static AlertDialog progressDialog;
    private TextView username,email,password;
    private Button signUp;

    //Instances
    TabLoginFragment tabLoginFragment;

    // Async task booleans
    boolean active = true;
    public static boolean success = false;
    public static boolean failure = false;
    public static boolean exist = false;
    public static boolean network = false;
    public static boolean unknown = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Text fields for user input
        username = view.findViewById(R.id.usernameTextView);
        email = view.findViewById(R.id.email_EditText);
        password = view.findViewById(R.id.pass_TextView);

        // Buttons
        signUp = view.findViewById(R.id.savePass_Button);

        // Instance of login to login after sign up
        tabLoginFragment = new TabLoginFragment();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_user();
            }


        });

        // Text listeners, will detect if the input is valid according to the standards set by the developers
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

        progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(getContext()).build();
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        validEmail = CheckValues.checkEmail(email.getText().toString());
        validPassword = CheckValues.checkPassword(password.getText().toString());
        validUsername = CheckValues.checkUsername(username.getText().toString());

        if(validEmail && validPassword && validUsername){
            new AsyncNewUser().execute(email.getText().toString(),password.getText().toString(),username.getText().toString());
            waitForResults();
        } else{
            progressDialog.dismiss();
            AlertDialogBoxes.AlertDialog("Fejl","Teksten vil blive grøn når det intastede er gyldigt. Tryk på spørgsmålstegnet for mere info","Ok",getActivity());
        }
    }

    public void waitForResults() {
        new Thread(new Runnable() {
            public void run() {
                while(active){
                    try {
                        if(success){
                            tabLoginFragment.attempt_login(email.getText().toString(),password.getText().toString(),true,getActivity());
                            active = false;
                        }else if(failure){

                            active = false;
                            progressDialog.dismiss();

                        }else if(exist){

                            progressDialog.dismiss();
                            active = false;

                        }else if(network){

                            progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUI("Fejl","Kontroller at telefonen har forbindelse til internettet",getActivity());
                            active = false;

                        }else if(unknown){

                            progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUI("Ukendt fejl","Prøv igen eller kontakt support",getActivity());
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



    static void setBooleans(){
        success = false;
        failure = false;
        exist = false;
        network = false;
        unknown = false;
    }
}
