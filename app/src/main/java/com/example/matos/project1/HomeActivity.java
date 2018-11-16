package com.example.matos.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button scan, browse, favorites, recents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        scan = findViewById(R.id.scan);
        browse = findViewById(R.id.browse);
        favorites = findViewById(R.id.favorites);
        recents = findViewById(R.id.recent);

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorites();
            }
        });

    }

    public void favorites() {
        Intent intent = new Intent(this, ProductList.class);
        startActivity(intent);
    }
}
