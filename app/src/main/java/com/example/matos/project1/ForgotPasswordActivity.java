package com.example.matos.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email_EditText;
    private Button send_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_EditText = findViewById(R.id.email_EditText);
        send_Button = findViewById(R.id.send_Button);

        send_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempt_passRecovery(email_EditText.getText().toString());
            }
        });


    }

    public void attempt_passRecovery(String email){


    }
}
