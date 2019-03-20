package com.example.matos.project1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

public class Product extends AppCompatActivity {


    RatingBar ratingBar;
    TextView score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        postponeEnterTransition();

        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        final TextView score = (TextView) findViewById(R.id.productRating);
        
        //String barcode = getIntent().getExtras().getString("Barcode");

        score.setText("4.0");

        new testWait().execute();

    }

    private class testWait extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... voids) {

            //Make http connection and apply JSON to layout

            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {

            startPostponedEnterTransition();
        }
    }

}
