package com.example.matos.project1.Users;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import dmax.dialog.SpotsDialog;

import com.example.matos.project1.AlertDialogBoxes;
import com.example.matos.project1.Menu.HomeActivity;
import com.example.matos.project1.R;

import static java.lang.Thread.sleep;

public class TabLoginFragment extends Fragment {

    // Text views, buttons etc
    private EditText email, password;
    private Button login;
    private TextView forgotPass;
    private CheckBox rememberMe_checkBox;

    //Progress dialog and context
    public static AlertDialog progressDialog;
    Context context;

    // Async task booleans
    public static boolean success = false;
    public static boolean failure = false;
    public static boolean network = false;
    boolean active = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        context = getContext();
        email = view.findViewById(R.id.email_EditText);
        password = view.findViewById(R.id.pass_TextView);
        forgotPass = view.findViewById(R.id.forgotPassTextView);
        rememberMe_checkBox = view.findViewById(R.id.checkBox);

        if(rememberMe_checkBox.isChecked()){
            System.out.println("checked");
        }

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
                startActivity(intent);
                if(rememberMe_checkBox.isChecked()){
                    System.out.println("ischecked");
                }
            }
        });



        login = view.findViewById(R.id.savePass_Button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // be able to login without connection to a server - Testing purposes
                if (email.getText().toString().equals("a") && password.getText().toString().equals("a")){
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    attempt_login(email.getText().toString(),password.getText().toString(),false,context);
                }
            }
        });

    }

    public void attempt_login(String email,String password, boolean signup, Context context1) {

        boolean check = true;
        boolean validEmail = CheckValues.checkEmail(email);

        if(!signup){
            progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(getContext()).build();
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        if(!validEmail){
            progressDialog.dismiss();
            AlertDialogBoxes.AlertDialog("Fejl","Den indstastede email er ikke gyldig. Login kan kun ske ved brug af email","Ok",getActivity());
        }else if(email.length() == 0 || password.length() == 0){
            progressDialog.dismiss();

            AlertDialogBoxes.AlertDialog("Fejl","Email og/eller password kan ikke være tom","Ok",getActivity());
            check = false;
        }

        if(check && validEmail){
            new AsyncLogin().execute(email,password);
            waitForResults(signup);
        }

    }

    public void waitForResults(final boolean signup) {
        active = true;

        new Thread(new Runnable() {
            public void run() {
                while(active){
                    try {
                        if(success){
                            if(signup){
                                LoginActivity loginActivity = LoginActivity.getInstance();
                                System.out.println(loginActivity);
                                loginActivity.goToHome();
                            }else{
                                progressDialog.dismiss();
                                Intent intent = new Intent(getContext(),HomeActivity.class);
                                startActivity(intent);
                            }
                            active = false;
                        }else if(failure){

                            progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUI("Fejl","Forkert email og/eller adgangskode. Prøv igen eller gå til reset password",getActivity());
                            active = false;

                        }else if(network){

                            progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUI("Fejl","Kontroller at telefonen har forbindelse til internettet",getActivity());
                            active = false;
                        }
                        Thread.sleep(200);
                    } catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }).start();
    }//hello

    static void setBooleans(){
        success = false;
        failure = false;
        network = false;
    }
}
