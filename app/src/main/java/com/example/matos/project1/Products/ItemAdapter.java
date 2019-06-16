package com.example.matos.project1.Products;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.matos.project1.R;
import com.example.matos.project1.SavedValues;
import com.example.matos.project1.Services;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private JSONArray jsons;
    private Context c;

    public ItemAdapter(Context c, JSONArray jsons){
        this.c = c;
        this.jsons = jsons;

        for(int i = 0; i < jsons.length(); i++){
            try {
                JSONObject json = jsons.getJSONObject(i);
                String imageString = json.getString("productmainimage");

                imageString = imageString.replace("\\/", "/");
                imageString = imageString.replace(" ", "+");

                json.put("productmainimage", imageString);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return jsons.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return jsons.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        JSONObject json = null;
        try {
            json = jsons.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        View v = mInflater.inflate(R.layout.product_list_view, null);

        final TextView productNameTextView = v.findViewById(R.id.productName);
        final TextView productRating = v.findViewById(R.id.productRating);
        final ImageView heartImage = v.findViewById(R.id.heartImageView);
        final ImageView productImage = v.findViewById(R.id.productImageView);
        ProgressBar productRatingBar = v.findViewById(R.id.productRatingBar);
        final ImageView heartImageView = v.findViewById(R.id.heartImageView);
        ImageView ovenImageView = v.findViewById(R.id.hotwater);
        ImageView microwaveImageView = v.findViewById(R.id.microwaveImageView);
        ImageView stoveImageView = v.findViewById(R.id.stoveImageView);
        ImageView hotwaterImageView = v.findViewById(R.id.hotwaterImageView);

        try {
            productNameTextView.setText(json.getString("productname"));
            productImage.setImageBitmap(Services.StringToBitMap(json.getString("productmainimage")));


            double rating = json.getDouble("avgrating");
            productRating.setText(String.format("%.1f", rating));
            productRatingBar.setProgress((int) (rating*20));

            if(json.getInt("inFavorites") == 1){
                heartImageView.setImageResource(R.drawable.filledheart);
            } else{
                heartImageView.setImageResource(R.drawable.emptyheart);
            }

            //TEST
            ovenImageView.setImageResource(R.drawable.ic_oven);
            microwaveImageView.setImageResource(R.drawable.ic_microwave);
            stoveImageView.setImageResource(R.drawable.ic_stove);
            hotwaterImageView.setImageResource(R.drawable.ic_hotwater);


            if(json.getInt("ovn") == 1) {ovenImageView.setBackgroundResource(R.drawable.custom_round);}
            if(json.getInt("grill") == 1) {hotwaterImageView.setBackgroundResource(R.drawable.custom_round);}
            if(json.getInt("komfur") == 1) {stoveImageView.setBackgroundResource(R.drawable.custom_round);}
            if(json.getInt("mikroovn") == 1) {microwaveImageView.setBackgroundResource(R.drawable.custom_round);}


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //OnClickListeners
        heartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //heart pressed
                String username = SavedValues.getInstance().getEmail();
                try {
                    JSONObject json = jsons.getJSONObject(i);
                    if(json.getInt("inFavorites") == 0){
                        new sendPostAPI().execute("favorites.php?username=" + username + "&barcode=" + json.getString("barcode"));
                        heartImageView.setImageResource(R.drawable.filledheart);
                        json.put("inFavorites", 1);
                    } else {
                        new sendPostAPI().execute("favorites.php?delete=1&username=" + username + "&barcode=" + json.getString("barcode"));
                        heartImageView.setImageResource(R.drawable.emptyheart);
                        json.put("inFavorites", 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("I have been clicked :D");

                try {
                    System.out.println(jsons.getJSONObject(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Open Productscreen for below id

                String barcode = "";
                try {
                    barcode = jsons.getJSONObject(i).getString("barcode");
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

    private class sendPostAPI extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String address = strings[0];
            Services.postAPI(address);
            return null;
        }

    }

}
