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
import android.widget.Button;
import android.widget.EditText;
import com.example.matos.project1.AlertDialogBoxes;
import com.example.matos.project1.R;
import dmax.dialog.SpotsDialog;

public class ForgotPasswordActivity extends AppCompatActivity {

    // Text fields
    private EditText email_EditText, editTextCode1, editTextCode2, editTextCode3, editTextCode4, editTextCode5;
    public EditText editTextEmail_dialog;

    public String codeString, verificationCode, stringEmail;
    // Buttons
    private Button send_Button, verifyCode_button;

    // Context
    private Context context;
    // Dialog
    public static AlertDialog progressDialog;
    public static Dialog dialog;

    // Async booleans
    public static boolean success = false;
    public static boolean failure = false;
    public static boolean network = false;
    private boolean active;

    public static boolean verification = false;
    public static boolean verificationError = false;

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
                //stringEmail = email_EditText.getText().toString();
                resetPassword();
            }
        });

    }

    Context getContext(){
        return context;
    }
    static void setBooleans(){
        success = false;
        failure = false;
        network = false;
        verification = false;
        verificationError = false;
    }


    void resetPassword(){
        progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(this).build();
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        new AsyncRequestResetPassword().execute(email_EditText.getText().toString());
        waitForResults();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void resetDialog(){

        bindViews(dialog);


        setTextListners();

        verifyCode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(dialog.getContext()).build();
                progressDialog.setMessage("Verifying code...");
                progressDialog.show();
                verificationCode = editTextCode1.getText().toString() + editTextCode2.getText().toString() + editTextCode3.getText().toString() +
                        editTextCode4.getText().toString() + editTextCode5.getText().toString();

                new AsyncCheckVerification().execute(email_EditText.getText().toString(),verificationCode);
                waitForResults();

            }
        });

    }

    void waitForResults(){
        active = true;
        new Thread(new Runnable() {
            public void run() {
                while(active){
                    active = true;
                    try {
                        if(success){

                            progressDialog.dismiss();
                            AlertDialogBoxes.resetDialogOnUI(getContext(),"Reset Password",stringEmail);
                            Thread.sleep(500);
                            resetDialog();
                            active = false;
                            success = false;

                        }else if(failure){

                            progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUIContext("Fejl","Den indtastede email findes ikke i systemet. Tjek venligst den indstastede email",getContext());
                            active = false;
                            failure = false;

                        }else if(network){
                            progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUIContext("Fejl","Kontroller at telefonen har forbindelse til internettet",getContext());
                            active = false;
                            network = false;

                        }else if(verification){
                            verification = false;
                            active = false;
                            progressDialog.dismiss();
                            dialog.dismiss();
                            Intent New_password = new Intent(ForgotPasswordActivity.this, ResetPassword.class);
                            New_password.putExtra("email",email_EditText.getText().toString());
                            startActivity(New_password);


                        }else if(verificationError){
                            progressDialog.dismiss();
                            AlertDialogBoxes.alertDialogOnUIContext("Fejl","Den indstastede kode stemmer ikke over ens, prøv igen",getContext());
                            active = false;
                            verificationError = false;

                        }

                        Thread.sleep(200);
                    } catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }).start();
    }

    // check if the user have used a correct verification code
    public boolean attempt_verificationCode() {



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
        //editTextEmail_dialog = dialog.findViewById(R.id.editTextEmail_dialog);
        verifyCode_button = dialog.findViewById(R.id.verifyCode_button);
        //editTextEmail_dialog.setText(stringEmail);

    }





}
