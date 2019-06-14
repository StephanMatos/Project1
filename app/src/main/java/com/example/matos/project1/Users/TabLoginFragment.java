package com.example.matos.project1.Users;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    // save login credentials
    private CheckBox saveLoginCheckBox;
    public static SharedPreferences mPrefs;
    public SharedPreferences.Editor prefsEditor;
    public static final String PREFS_NAME = "CheckboxFile";

    //Progress dialog and context
    public static AlertDialog progressDialog;
    Context context;

    // Async task booleans
    public static boolean success = false;
    public static boolean failure = false;
    public static boolean network = false;
    public static boolean unknown = false;
    boolean active = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);

        // Initializing activity Widgets
        bindWidget(view);
        //saveLoginCheckBox.setChecked(false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        context = getContext();

        // Check if the user have checked 'saveLoginCheckBox' from earlier
        automaticLogin();

        // If the user have forgotten his/hers password and want to reset password
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attempt_login(email.getText().toString(), password.getText().toString(), false);

            }
        });

    }

    public void attempt_login(String email,String password, boolean signup) {

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

        new Thread(new Runnable() {
            public void run() {

                active = true;

                while(active){
                    try {
                        if(success){
                            if(signup){
                                LoginActivity loginActivity = LoginActivity.getInstance();
                                loginActivity.goToHome();
                            }else{
                                progressDialog.dismiss();
                                if (saveLoginCheckBox.isChecked()) {
                                    prefsEditor.putBoolean("CheckBox", true);
                                    prefsEditor.putString("Email", email.getText().toString());
                                    prefsEditor.putString("Password", password.getText().toString());
                                    prefsEditor.apply();

                                } else {
                                    prefsEditor.putBoolean("CheckBox", false);
                                    prefsEditor.apply();

                                }
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                startActivity(intent);

                            }
                            active = false;
                        } else if (failure) {

                            progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUI("Fejl","Forkert email og/eller adgangskode. Prøv igen eller gå til reset password",getActivity());
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
        network = false;
        unknown = false;
    }

    // Check if the user have checked 'saveLoginCheckBox' from earlier
    private void automaticLogin() {

        mPrefs = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        System.out.println(mPrefs);
        prefsEditor = mPrefs.edit();

        if (mPrefs.getBoolean("CheckBox",true)){
            saveLoginCheckBox.setChecked(true);

            String shared_email = mPrefs.getString("Email","");
            email.setText(shared_email);

            String shared_password = mPrefs.getString("Password","");
            password.setText(shared_password);
            prefsEditor.apply();

            attempt_login(shared_email,shared_password, false);

        } else {
            email.setText(mPrefs.getString("Email",""));

            if(email.getText().toString().length() > 0){
                password.requestFocus();
            }

        }
    }

    // Initializing activity Widgets
    private void bindWidget(View view) {
        email = view.findViewById(R.id.email_EditText);
        password = view.findViewById(R.id.pass_TextView);
        forgotPass = view.findViewById(R.id.forgotPassTextView);
        saveLoginCheckBox = view.findViewById(R.id.checkBox);
        login = view.findViewById(R.id.savePass_Button);

    }
}
