package com.example.matos.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    ListView listView;
    ArrayList<JSONObject> jsons = new ArrayList<JSONObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        listView = findViewById(R.id.listView);

    }
}
