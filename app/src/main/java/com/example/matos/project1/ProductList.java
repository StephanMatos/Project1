package com.example.matos.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
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

        jsons = JSONTest();
        ItemAdapter itemAdapter = new ItemAdapter(this, jsons);
        listView.setAdapter(itemAdapter);

    }

    ArrayList<JSONObject> JSONTest(){
        ArrayList<JSONObject> jsons = new ArrayList<JSONObject>();

        for(int i = 0; i < 3; i++){
            JSONObject json = new JSONObject();
            try {
                json.put("name", "Matos Speciale" + i+1);
                json.put("rating", 1.3+i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsons;
    }

}
