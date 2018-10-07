package com.example.matos.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    ArrayList<JSONObject> jsons;

    public ItemAdapter(Context c, ArrayList<JSONObject> jsons){
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        JSONObject json = jsons.get(i);

        View v = mInflater.inflate(R.layout.product_list_view, null);
        TextView productNameTextView = v.findViewById(R.id.productNameTextView);
        TextView productRating = v.findViewById(R.id.productRating);
        ProgressBar productRatingBar = v.findViewById(R.id.productRatingBar);

        try {
            productNameTextView.setText(json.getString("name"));

            String ratingStr = json.getString("rating");
            productRating.setText(ratingStr);
            int rating = Integer.parseInt(ratingStr);
            productRatingBar.setProgress(rating*50);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }
}
