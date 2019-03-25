package com.example.matos.project1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Product extends AppCompatActivity {


    RatingBar ratingBar;
    TextView productRating, productName, productState;
    String barcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        postponeEnterTransition();

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        productRating = (TextView) findViewById(R.id.productRating);
        productName = (TextView) findViewById(R.id.productName);
        productState = (TextView) findViewById(R.id.productState);
        
        barcode = getIntent().getExtras().getString("barcode");


        new testWait().execute();

    }

    private class testWait extends AsyncTask<Void, Void, Void> {

        JSONObject json;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... voids) {

            //Make http connection and apply JSON to layout

            //test delay and jsonProduct
            try {
                Thread.sleep(1000);
                json = Services.testProductJSON(barcode);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {

            try {
                productName.setText(json.getString("name"));
                productRating.setText(String.format("%.2f", json.getDouble("rating")));
                productState.setText(json.getString("state"));
                ratingBar.setProgress((int) (json.getDouble("rating")));
            } catch (Exception e) {
                e.printStackTrace();
            }

            startPostponedEnterTransition();
        }
    }

}
