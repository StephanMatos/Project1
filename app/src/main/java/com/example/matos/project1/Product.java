package com.example.matos.project1;

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

public class Product extends AppCompatActivity {


    RatingBar ratingBar;
    TextView productRating, productName, productState, descriptionText;
    ImageView heartImageView, ovenImageView, microwaveImageView, stoveImageView, hotwaterImageView;
    String barcode;
    JSONObject json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        postponeEnterTransition();


        ovenImageView = findViewById(R.id.oven);
        microwaveImageView = findViewById(R.id.microwave);
        stoveImageView = findViewById(R.id.stove);
        hotwaterImageView = findViewById(R.id.hotwater);
        ovenImageView.setImageResource(R.drawable.ic_oven);
        microwaveImageView.setImageResource(R.drawable.ic_microwave);
        stoveImageView.setImageResource(R.drawable.ic_stove);
        hotwaterImageView.setImageResource(R.drawable.ic_hotwater);

        ratingBar = findViewById(R.id.ratingBar);
        productRating = findViewById(R.id.productRating);
        productName = findViewById(R.id.productName);
        descriptionText =  findViewById(R.id.descriptionText);
        productState = findViewById(R.id.productState);
        heartImageView =  findViewById(R.id.heart);
        
        barcode = getIntent().getExtras().getString("barcode");

        heartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = SavedValues.getInstance().getEmail();
                try {
                    if(json.getInt("inFavorites") == 0){
                        new sendPostAPI().execute("favorites.php?username=" + username + "&barcode=" + json.getString("barcode"));
                        heartImageView.setImageResource(R.drawable.filledheart);
                    } else {
                        new sendPostAPI().execute("favorites.php?delete=1&username=" + username + "&barcode=" + json.getString("barcode"));
                        heartImageView.setImageResource(R.drawable.emptyheart);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        new getProduct().execute();

    }

    private class getProduct extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... voids) {

            String username = SavedValues.getInstance().getEmail();
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

                if(json.getInt("ovn") == 1) {ovenImageView.setBackgroundResource(R.drawable.custom_round);}
                if(json.getInt("grill") == 1) {hotwaterImageView.setBackgroundResource(R.drawable.custom_round);}
                if(json.getInt("komfur") == 1) {stoveImageView.setBackgroundResource(R.drawable.custom_round);}
                if(json.getInt("mikroovn") == 1) {microwaveImageView.setBackgroundResource(R.drawable.custom_round);}

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

    private class sendPostAPI extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String address = strings[0];
            Services.postAPI(address);
            return null;
        }

    }

}
