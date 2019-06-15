package com.example.matos.project1.Products;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.RatingBar;

import com.example.matos.project1.R;
import com.example.matos.project1.SavedValues;
import com.example.matos.project1.Services;


public class RateDialog extends Dialog {

    RatingBar ratingBar;
    int productID;

    public RateDialog(Activity a, int productID) {
        super(a);
        this.productID = productID;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_rate);

        ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                System.out.println("rating is set to " + ((double) ratingBar.getProgress())/2.0 );
                new RateAsync().execute();
                cancel();
            }
            
        });

    }


    public class RateAsync extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String username = SavedValues.getInstance().getEmail();
            String address = "rating.php?username=" + username + "&productID=" + productID+ "&rating=" + ((double) ratingBar.getProgress())/2.0;
            Services.postAPI(address);
            return null;
        }
    }

}
