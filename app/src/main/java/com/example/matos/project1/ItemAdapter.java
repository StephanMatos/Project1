package com.example.matos.project1;

import android.content.Context;
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
        TextView productNameTextView = v.findViewById(R.id.productNameTextView);
        TextView productRating = v.findViewById(R.id.productRating);
        ProgressBar productRatingBar = v.findViewById(R.id.productRatingBar);
        ImageView heartImageView = v.findViewById(R.id.heartImageView);

        try {
            productNameTextView.setText(json.getString("name"));

            String ratingStr = json.getString("rating");
            productRating.setText(ratingStr);
            double rating = Double.parseDouble(ratingStr);
            //double rating = 2;
            productRatingBar.setProgress((int) (rating*20));
            if(json.getBoolean("inFavorite")){
                heartImageView.setImageResource(R.drawable.filledheart);
            } else{
                heartImageView.setImageResource(R.drawable.emptyheart);
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
                //String id = jsons.get(i).getString("id");
                //Intent productIntent = new Intent(c, Product.class);
                //productIntent.putExtra("id", id);
            }
        });

        return v;
    }
}
