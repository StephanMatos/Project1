package com.example.matos.project1.Users;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

import com.example.matos.project1.AlertDialogBoxes;
import com.example.matos.project1.AsyncTask.AsyncNewUser;
import com.example.matos.project1.R;

import dmax.dialog.SpotsDialog;

public class TabSignupFragment extends Fragment {

    // Validation of user input
    boolean validEmail = false;
    boolean validPassword = false;
    boolean validUsername = false;
    GradientDrawable drawable_username;
    GradientDrawable drawable_email;
    GradientDrawable drawable_password;

    public static AlertDialog progressDialog;
    private TextView username,email,password;
    private Button signUp;

    //Instances
    TabLoginFragment tabLoginFragment;

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

        // Change Drawable strokecolor based on user inputs, stating of with lightgray
        drawable_username = (GradientDrawable)username.getBackground();
        drawable_email = (GradientDrawable)email.getBackground();
        drawable_password = (GradientDrawable)password.getBackground();
        drawable_username.setStroke(2, Color.rgb(192,192,192));
        drawable_email.setStroke(2, Color.rgb(192,192,192));
        drawable_password.setStroke(2, Color.rgb(192,192,192));


        // Buttons
        signUp = view.findViewById(R.id.savePass_Button);

        // Instance of login to login after sign up
        tabLoginFragment = new TabLoginFragment();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_user(getContext(),getActivity());
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
                    drawable_username.setStroke(3, Color.GREEN);
                }else{
                    drawable_username.setStroke(3, Color.RED);

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
                    drawable_email.setStroke(3, Color.GREEN);
                }else{
                    drawable_email.setStroke(3, Color.RED);
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

                    //password.setTextColor(Color.GREEN);
                    drawable_password.setStroke(3, Color.GREEN);
                }else{
                    //password.setTextColor(Color.RED);
                    drawable_password.setStroke(3, Color.RED);

                }

            }
        });

    }

    private void create_user(Context context,Activity activity) {

        progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(context).build();
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        validEmail = CheckValues.checkEmail(email.getText().toString());
        validPassword = CheckValues.checkPassword(password.getText().toString());
        validUsername = CheckValues.checkUsername(username.getText().toString());

        if(validEmail && validPassword && validUsername){
            new AsyncNewUser().execute(email.getText().toString(),password.getText().toString(),username.getText().toString());
            ResultThread.waitForResults(true,activity,context,email.getText().toString(),password.getText().toString(),false);

        } else{
            progressDialog.dismiss();
            AlertDialogBoxes.AlertDialog("Fejl","Teksten vil blive grøn når det intastede er gyldigt. Tryk på spørgsmålstegnet for mere info","Ok",getActivity());
        }
    }
}
