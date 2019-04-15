package com.example.matos.project1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Product extends AppCompatActivity {


    RatingBar ratingBar;
    TextView productRating, productName, productState, descriptionText;
    ImageView heartImageView;
    String barcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        postponeEnterTransition();

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        productRating = (TextView) findViewById(R.id.productRating);
        productName = (TextView) findViewById(R.id.productName);
        descriptionText = (TextView) findViewById(R.id.descriptionText);
        productState = (TextView) findViewById(R.id.productState);
        heartImageView = (ImageView) findViewById(R.id.heart);
        
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

            String username = "schmidt13@live.dk";
            String data = Services.callAPI("products.php?barcode=" + barcode + "&username=" + username);
            System.out.println("data is");
            System.out.println(data);
            JSONArray jsons = null;
            try {
                jsons = new JSONArray(data);
                json = jsons.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            //test
            try {
                productName.setText(json.getString("productname"));
                productRating.setText(String.format("%.2f", json.getDouble("avgrating")));
                descriptionText.setText(json.getString("description"));
                productState.setText(json.getString("state"));
                ratingBar.setProgress((int) (json.getDouble("avgrating")));

                if(json.getInt("inFavorites") == 1){
                    heartImageView.setImageResource(R.drawable.filledheart);
                } else{
                    heartImageView.setImageResource(R.drawable.emptyheart);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            startPostponedEnterTransition();
        }
    }

}
