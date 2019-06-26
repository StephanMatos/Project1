package com.example.matos.project1.Users;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matos.project1.AlertDialogBoxes;
import com.example.matos.project1.AsyncTask.AsyncCheckVerification;
import com.example.matos.project1.AsyncTask.AsyncRequestResetPassword;
import com.example.matos.project1.R;
import dmax.dialog.SpotsDialog;

public class ForgotPasswordActivity extends AppCompatActivity {

    // Text fields
    private EditText email_EditText, editTextCode1, editTextCode2, editTextCode3, editTextCode4, editTextCode5;
    public TextView reopen_verificationDialog_TextView;

    public String codeString, verificationCode;
    // Buttons
    private Button send_Button, verifyCode_button;

    //ImageViews
    private ImageView help_button;

    // Context
    private Context context;
    // Dialog
    public static AlertDialog progressDialog;
    public static Dialog dialog;

    // Async booleans
    public static boolean success = false;
    public static boolean failure = false;
    public static boolean network = false;
    public static boolean unknown = false;
    private boolean active;

    public static boolean verification = false;
    public static boolean verificationError = false;

    // Drawables
    GradientDrawable drawable_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        // Initializing activity Widgets
        bindViews();

        context = this;

        send_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email_EditText.length() == 0) {
                    AlertDialogBoxes.alertDialogOnUIContext("Fejl","Feltet 'Email' skal udfyldes",getContext());

                } else {

                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialogview_passreset);
                    dialog.setTitle("Gendan Password");
                    TextView emailView = dialog.findViewById(R.id.textViewEmail);
                    emailView.setText(email_EditText.getText().toString());
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    dialog.show();

                    resetDialog();

                    new AsyncRequestResetPassword().execute(email_EditText.getText().toString());
                    ResultThread.waitForResults(false, getParent(),getContext(),email_EditText.getText().toString(),"null",false);

                }
            }
        });

        // If the user press the help ('?') icon in the top, a dialog with instructions will follow
        help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create a Dialog component
                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.dialogview_help_passreset);
                dialog.setTitle("Reset password");

                Button ok_Button = dialog.findViewById(R.id.ok_button);

                ok_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        reopen_verificationDialog_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email_EditText.length() == 0) {
                    AlertDialogBoxes.alertDialogOnUIContext("Fejl","Feltet 'Email' skal udfyldes",getContext());

                } else {

                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialogview_passreset);
                    dialog.setTitle("Gendan Password");
                    TextView emailView = dialog.findViewById(R.id.textViewEmail);
                    emailView.setText(email_EditText.getText().toString());
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    dialog.show();
                    resetDialog();
                }


            }
        });
    }

    Context getContext(){
        return context;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    void resetDialog(){

        bindDialogViews(dialog);

        setTextListners();

        verifyCode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click");
                progressDialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(dialog.getContext()).build();
                progressDialog.setMessage("Verifying code...");
                progressDialog.show();
                verificationCode = editTextCode1.getText().toString() + editTextCode2.getText().toString() + editTextCode3.getText().toString() +
                        editTextCode4.getText().toString() + editTextCode5.getText().toString();

                new AsyncCheckVerification().execute(email_EditText.getText().toString(),verificationCode);
                ResultThread.waitForResults(false,getParent(),getContext(),email_EditText.getText().toString(),"null",false);

            }
        });

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
                    //return;
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

    //initializing dialog Widgets
    private void bindDialogViews(Dialog dialog) {
        editTextCode1 = dialog.findViewById(R.id.editTextCode1);
        editTextCode2 = dialog.findViewById(R.id.editTextCode2);
        editTextCode3 = dialog.findViewById(R.id.editTextCode3);
        editTextCode4 = dialog.findViewById(R.id.editTextCode4);
        editTextCode5 = dialog.findViewById(R.id.editTextCode5);
        verifyCode_button = dialog.findViewById(R.id.verifyCode_button);


    }

    // Initializing activity Widgets
    private void bindViews () {
        email_EditText = findViewById(R.id.email_EditText);
        send_Button = findViewById(R.id.savePass_Button);
        help_button = findViewById(R.id.help_button);
        reopen_verificationDialog_TextView = findViewById(R.id.reopen_verificationDialog_TextView);

        drawable_email = (GradientDrawable)email_EditText.getBackground();
        drawable_email.setStroke(2, Color.rgb(192,192,192));
    }

}


