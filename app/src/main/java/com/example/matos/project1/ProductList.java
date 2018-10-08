package com.example.matos.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    ListView listView;
    ArrayList<JSONObject> jsons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        listView = findViewById(R.id.listView);

        jsons = JSONTest();
        System.out.println("JSONS size: " + jsons.size());
        ItemAdapter itemAdapter = new ItemAdapter(this, jsons);
        listView.setAdapter(itemAdapter);

    }

    ArrayList<JSONObject> JSONTest(){
        ArrayList<JSONObject> jsons = new ArrayList<>();

        for(int i = 0; i < 13; i++){
            JSONObject json = new JSONObject();
            try {
                json.put("name", "Matos Speciale" + (i+1));

                DecimalFormat df1 = new DecimalFormat(".#");
                json.put("rating", df1.format(1.3+i*0.1));

                if(i % 2 == 0){
                    json.put("inFavorite", true);
                } else{
                    json.put("inFavorite", false);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsons.add(json);
        }
        return jsons;
    }

}
