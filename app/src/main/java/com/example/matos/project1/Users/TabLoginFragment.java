package com.example.matos.project1.Users;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.example.matos.project1.AsyncTask.AsyncLogin;
import com.example.matos.project1.R;

import static java.lang.Thread.sleep;

public class TabLoginFragment extends Fragment {

    // Text views, buttons etc
    private EditText email, password;
    private Button login;
    private TextView forgotPass;

    // Drawables
    GradientDrawable drawable_email;
    GradientDrawable drawable_password;

    // save login credentials
    public CheckBox saveLoginCheckBox;
    public static SharedPreferences mPrefs;
    public SharedPreferences.Editor prefsEditor;
    public static final String PREFS_NAME = "CheckboxFile";
    private Context context;

    //Progress dialog and context
    public static AlertDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);

        // Initializing activity Widgets
        bindWidget(view);

        // Setting the stroke of the drawable to lightgray
        drawable_email = (GradientDrawable)email.getBackground();
        drawable_email.setStroke(2, Color.rgb(192,192,192));
        drawable_password = (GradientDrawable)password.getBackground();
        drawable_password.setStroke(2, Color.rgb(192,192,192));

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        try{
            mPrefs = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            prefsEditor = mPrefs.edit();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        context = this.getContext();
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
                attempt_login(email.getText().toString(), password.getText().toString(), false,getActivity());
            }
        });

        saveLoginCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (saveLoginCheckBox.isChecked()) {
                    prefsEditor.putBoolean("CheckBox", true);
                    prefsEditor.putString("Email", email.getText().toString());
                    prefsEditor.putString("Password", password.getText().toString());
                    prefsEditor.apply();

                } else {
                    prefsEditor.putBoolean("CheckBox", false);

                    prefsEditor.apply();

                }
            }
        });

    }

    public void attempt_login(String email,String password, boolean signup,final Activity activity) {


        boolean check = true;
        boolean validEmail = CheckValues.checkEmail(email);

        if(!signup){
            progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(getContext()).build();
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        if(!validEmail){
            progressDialog.dismiss();
            AlertDialogBoxes.AlertDialog("Fejl","Den indstastede email er ikke gyldig. Login kan kun ske ved brug af email","Ok",activity);
        }else if(email.length() == 0 || password.length() == 0){
            progressDialog.dismiss();

            AlertDialogBoxes.AlertDialog("Fejl","Email og/eller password kan ikke være tom","Ok",activity);
            check = false;
        }

        if(check && validEmail){
            new AsyncLogin().execute(email,password);
            ResultThread.waitForResults(signup,activity,context,email,password,false);
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
