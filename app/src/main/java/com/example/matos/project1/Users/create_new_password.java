package com.example.matos.project1.Users;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.example.matos.project1.R;

public class create_new_password extends AppCompatActivity {

    private EditText newPassword_EditText, verifyPassword_EditText;
    private Button savePass_Button;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null) {
            email =(String) bundle.get("email");
            System.out.println(email);
        }
//hello
        newPassword_EditText = findViewById(R.id.newPassword_EditText);
        verifyPassword_EditText = findViewById(R.id.verifyPassword_EditText);
        savePass_Button = findViewById(R.id.savePass_Button);

        savePass_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkpasswords();
            }
        });
    }


    public void checkpasswords(){

        if (newPassword_EditText.getText() == verifyPassword_EditText.getText()) {
            Intent LoginActivity = new Intent(create_new_password.this, LoginActivity.class);
            startActivity(LoginActivity);
        }

    }
}
