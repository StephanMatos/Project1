package com.example.matos.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Product extends AppCompatActivity {


    RatingBar ratingBar;
    TextView score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        final TextView score = (TextView) findViewById(R.id.score);

        DecimalFormat df1 = new DecimalFormat(".#");
        score.setText("" + df1.format(4.0));

        ratingBar.setRating(Float.parseFloat(df1.format(4.0)));

    }


}
