package com.example.matos.project1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<JSONObject> jsons;
    private Context c;

    ItemAdapter(Context c, ArrayList<JSONObject> jsons){
        this.c = c;
        this.jsons = jsons;

        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return jsons.size();
    }

    @Override
    public Object getItem(int i) {
        return jsons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        JSONObject json = jsons.get(i);

        View v = mInflater.inflate(R.layout.product_list_view, null);




        final TextView productNameTextView = v.findViewById(R.id.productName);
        final TextView productRating = v.findViewById(R.id.productRating);
        final ImageView heartImage = v.findViewById(R.id.heartImageView);
        final ImageView productImage = v.findViewById(R.id.productImageView);
        ProgressBar productRatingBar = v.findViewById(R.id.productRatingBar);
        ImageView heartImageView = v.findViewById(R.id.heartImageView);
        ImageView ovenImageView = v.findViewById(R.id.ovenImageView);
        ImageView microwaveImageView = v.findViewById(R.id.microwaveImageView);
        ImageView stoveImageView = v.findViewById(R.id.stoveImageView);
        ImageView hotwaterImageView = v.findViewById(R.id.hotwaterImageView);

        try {
            productNameTextView.setText(json.getString("name"));

            double rating = json.getDouble("rating");
            productRating.setText(String.format("%.2f", rating));
            productRatingBar.setProgress((int) (rating*20));

            if(json.getBoolean("inFavorite")){
                heartImageView.setImageResource(R.drawable.filledheart);
            } else{
                heartImageView.setImageResource(R.drawable.emptyheart);
            }

            //TEST
            ovenImageView.setImageResource(R.drawable.ic_oven);
            microwaveImageView.setImageResource(R.drawable.ic_microwave);
            stoveImageView.setImageResource(R.drawable.ic_stove);
            hotwaterImageView.setImageResource(R.drawable.ic_hotwater);

            if(i % 2 == 0){
                ovenImageView.setBackgroundResource(R.drawable.custom_round);
                hotwaterImageView.setBackgroundResource(R.drawable.custom_round);
            }
            if(i % 3 == 0){
                microwaveImageView.setBackgroundResource(R.drawable.custom_round);
                stoveImageView.setBackgroundResource(R.drawable.custom_round);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //OnClickListeners
        heartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    System.out.println("Heart has been pressed for " + jsons.get(i).getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("I have been clicked :D");

                //Open Productscreen for below id

                String barcode = "";
                try {
                    barcode = jsons.get(i).getString("barcode");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent productIntent = new Intent(c, Product.class);
                productIntent.putExtra("barcode", barcode);

                Pair p1 = Pair.create(productNameTextView, ViewCompat.getTransitionName(productNameTextView));
                Pair p2 = Pair.create(productRating, ViewCompat.getTransitionName(productRating));
                Pair p3 = Pair.create(heartImage, ViewCompat.getTransitionName(heartImage));
                Pair p4 = Pair.create(productImage, ViewCompat.getTransitionName(productImage));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) c, p1, p2, p3, p4);

                c.startActivity(productIntent, options.toBundle());
            }
        });

        return v;
    }
}
