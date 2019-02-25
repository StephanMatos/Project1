package com.example.matos.project1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    ImageView testBtn;
    ListView listView;
    ArrayList<JSONObject> jsons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        listView = findViewById(R.id.listView);

        new setupList().execute();


        //TestAPI
        testBtn = findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new APITest().execute();

            }
        });

    }

    private class setupList extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(ProductList.this, "Product List","Loading. Please wait...");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            jsons = JSONTest();

            ItemAdapter itemAdapter = new ItemAdapter(ProductList.this, jsons);
            listView.setAdapter(itemAdapter);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }

    ArrayList<JSONObject> JSONTest(){
        ArrayList<JSONObject> jsons = new ArrayList<>();

        for(int i = 0; i < 23; i++){
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

    private class APITest extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(Void... voids) {

            return Services.callAPI("SELECT * FROM 'users'");

        }

        @Override
        protected void onPostExecute(String response){

            System.out.println("Response from API is: " + response);

        }

    }

}
