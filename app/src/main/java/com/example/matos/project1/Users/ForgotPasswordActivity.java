package com.example.matos.project1.Users;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.matos.project1.R;
import com.example.matos.project1.SplashScreenActivity;

import dmax.dialog.SpotsDialog;

import static java.lang.Thread.sleep;


public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email_EditText, editTextCode1, editTextCode2, editTextCode3, editTextCode4, editTextCode5;
    private TextView textViewEmail;
    private Button send_Button, verifyCode_button;
    private Context context;
    public static AlertDialog progressDialog;
    public static boolean success = false;
    public static boolean failure = false;
    public String codeString, verificationCode, stringEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email_EditText = findViewById(R.id.email_EditText);
        send_Button = findViewById(R.id.savePass_Button);
        context = this;
        send_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringEmail = email_EditText.getText().toString();
                resetPassword();
            }
        });

    }
    Context getContext(){
        return context;
    }


    void resetPassword(){
        progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(this).build();

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        new AsyncResetPassword().execute(email_EditText.getText().toString());
        runn();

        //show();
    }

    void show(){
        final Dialog dialog = new Dialog(ForgotPasswordActivity.this);

        dialog.setContentView(R.layout.dialogview_passreset);
        dialog.setTitle("verification code");
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
        bindViews(dialog);
        textViewEmail.setText(stringEmail);
        setTextListners();


        verifyCode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(dialog.getContext()).build();
                progressDialog.setMessage("Verifying code...");
                progressDialog.show();

                if (attempt_verificationCode()) {

                    progressDialog.dismiss();
                    dialog.dismiss();
                    Intent New_password = new Intent(ForgotPasswordActivity.this, create_new_password.class);
                    startActivity(New_password);


                }
            }
        });

    }


    void runn(){

        runOnUiThread(new Runnable() {
            boolean running = true;
            @Override
            public void run() {

                try {
                    while(running) {
                        System.out.println("running");
                        if(success){
                            progressDialog.dismiss();

                            running = false;
                            show();
                            return;

                        } else if (failure){
                            running = false;
                            System.out.println("dialog");
                            progressDialog.dismiss();
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Fejl")
                                    .setMessage("Den indtastede email findes ikke i systemet. Tjek venligst den indstastede email")
                                    .setNeutralButton("OK",null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();


                        }
                        sleep(300);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    // check if the user have used a correct verification code
    public boolean attempt_verificationCode() {

        verificationCode = editTextCode1.getText().toString() + editTextCode2.getText().toString() + editTextCode3.getText().toString() +
                editTextCode4.getText().toString() + editTextCode5.getText().toString();

        if (verificationCode.length() < 4) {
            return false;
        } else {
            return true;
        }
    }

    // Moving between the "boxes" based on the users input
    public void setTextListners() {
        editTextCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 1) {
                    return;
                } else if (editable.length() == 2) {
                    codeString = editTextCode1.getText().toString();
                    editTextCode1.setText(Character.toString(codeString.charAt(0)));
                    editTextCode2.setText(Character.toString(codeString.charAt(1)));
                    editTextCode1.clearFocus();
                    editTextCode2.requestFocus();
                    editTextCode2.setSelection(editTextCode2.getText().length());
                }

            }
        });
        editTextCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 1) {
                    return;
                } else if (editable.length() == 0 ) {
                    editTextCode2.clearFocus();
                    editTextCode1.requestFocus();
                    editTextCode1.setSelection(editTextCode1.getText().length());
                } else {
                    codeString = editTextCode2.getText().toString();
                    editTextCode2.setText(Character.toString(codeString.charAt(0)));
                    editTextCode3.setText(Character.toString(codeString.charAt(1)));
                    editTextCode2.clearFocus();
                    editTextCode3.requestFocus();
                    editTextCode3.setSelection(editTextCode3.getText().length());
                }

            }
        });
        editTextCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 1) {
                    return;
                } else if (editable.length() == 0) {
                    editTextCode3.clearFocus();
                    editTextCode2.requestFocus();
                    editTextCode2.setSelection(editTextCode2.getText().length());

                } else {
                    codeString = editTextCode3.getText().toString();
                    editTextCode3.setText(Character.toString(codeString.charAt(0)));
                    editTextCode4.setText(Character.toString(codeString.charAt(1)));
                    editTextCode3.clearFocus();
                    editTextCode4.requestFocus();
                    editTextCode4.setSelection(editTextCode4.getText().length());
                }

            }
        });
        editTextCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 1) {
                    return;
                } else if (editable.length() == 0) {
                    editTextCode4.clearFocus();
                    editTextCode3.requestFocus();
                    editTextCode3.setSelection(editTextCode3.getText().length());

                } else {
                    codeString = editTextCode4.getText().toString();
                    editTextCode4.setText(Character.toString(codeString.charAt(0)));
                    editTextCode5.setText(Character.toString(codeString.charAt(1)));
                    editTextCode4.clearFocus();
                    editTextCode5.requestFocus();
                    editTextCode5.setSelection(editTextCode5.getText().length());
                }

            }
        });
        editTextCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 0) {
                    editTextCode5.clearFocus();
                    editTextCode4.requestFocus();
                    editTextCode4.setSelection(editTextCode4.getText().length());
                }

            }
        });

    }




    private void bindViews(Dialog dialog) {
        //here initialize dialog components
        editTextCode1 = dialog.findViewById(R.id.editTextCode1);
        editTextCode2 = dialog.findViewById(R.id.editTextCode2);
        editTextCode3 = dialog.findViewById(R.id.editTextCode3);
        editTextCode4 = dialog.findViewById(R.id.editTextCode4);
        editTextCode5 = dialog.findViewById(R.id.editTextCode5);
        textViewEmail = dialog.findViewById(R.id.textViewEmail);
        verifyCode_button = dialog.findViewById(R.id.verifyCode_button);

    }





}
