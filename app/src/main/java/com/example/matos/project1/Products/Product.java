package com.example.matos.project1.Products;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matos.project1.AsyncTask.SetupList;
import com.example.matos.project1.R;
import com.example.matos.project1.SavedValues;
import com.example.matos.project1.Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Product extends AppCompatActivity {

    private int GALLERY = 1, CAMERA = 2;

    TextView rateBtn;
    RatingBar ratingBar;
    TextView productRating, productName, productState, descriptionText, productPrice;
    ImageView productImage, heartImageView, ovenImageView, microwaveImageView, stoveImageView, hotwaterImageView;
    Button addImageBtn;
    String barcode;
    int productID;
    JSONObject json;
    LinearLayout linearImageLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        postponeEnterTransition();

        rateBtn = findViewById(R.id.rateButton);
        linearImageLayout = findViewById(R.id.linear);
        addImageBtn = findViewById(R.id.addImageBtn);

        productImage = findViewById(R.id.productImage);

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
        productPrice = findViewById(R.id.productPrice);
        heartImageView =  findViewById(R.id.heart);
        
        barcode = Objects.requireNonNull(getIntent().getExtras()).getString("barcode");

        heartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = SavedValues.getInstance().getEmail();
                try {
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

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    RateDialog dialog = new RateDialog(Product.this, json.getInt("productID"));
                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });


        new getProduct().execute();
        new getImages().execute();
        new addToRecents().execute();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new SetupList().execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class addToRecents extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            String username = SavedValues.getInstance().getEmail();

            Services.postAPI("recents.php?username=" + username + "&barcode=" + barcode + "&delete=1");
            Services.postAPI("recents.php?username=" + username + "&barcode=" + barcode);

            return null;
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class getProduct extends AsyncTask<Void, Void, Void> {

        Bitmap productMainImageBitmap;

        @Override
        protected Void doInBackground(Void... voids) {

            String username = SavedValues.getInstance().getEmail();
            String data = Services.callAPI("products.php?barcode=" + barcode + "&username=" + username);
            JSONArray jsons;
            try {
                jsons = new JSONArray(data);
                json = jsons.getJSONObject(0);
                productMainImageBitmap = Services.StringToBitMap(json.getString("productmainimage"));
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(Void avoid) {
            try {
                productID = json.getInt("productID");
                productImage.setImageDrawable(RoundedImageCorners(productMainImageBitmap));
                productName.setText(json.getString("productname"));
                productRating.setText(String.format("%.1f", json.getDouble("avgrating")));
                productPrice.setText(String.format("%.2f", json.getDouble("price")));
                descriptionText.setText(json.getString("description"));
                productState.setText(json.getString("state"));
                ratingBar.setRating((float) json.getDouble("avgrating"));

                if(json.getInt("ovn") == 1) {ovenImageView.setBackgroundResource(R.drawable.custom_round);}
                if(json.getInt("grill") == 1) {hotwaterImageView.setBackgroundResource(R.drawable.custom_round);}
                if(json.getInt("komfur") == 1) {stoveImageView.setBackgroundResource(R.drawable.custom_round);}
                if(json.getInt("mikroovn") == 1) {microwaveImageView.setBackgroundResource(R.drawable.custom_round);}

                if(json.getInt("inFavorites") == 1){
                    heartImageView.setImageResource(R.drawable.filledheart);
                } else{
                    heartImageView.setImageResource(R.drawable.emptyheart);
                }

                //new getImages().execute();

            } catch (Exception e) {
                e.printStackTrace();
            }


            //startPostponedEnterTransition();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class sendPostAPI extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String address = strings[0];
            Services.postAPI(address);
            return null;
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class getImages extends AsyncTask<Void, Void, ArrayList<Bitmap>> {

        @Override
        protected ArrayList<Bitmap> doInBackground(Void... voids) {

            String data = Services.callAPI("uploadJPG.php?barcode=" + barcode);

            ArrayList<Bitmap> bitmaps = new ArrayList<>();

            try {
                JSONArray jsons = new JSONArray(data);
                JSONObject json = jsons.getJSONObject(0);

                int i = 0;
                while(json.has(""+i)){
                    bitmaps.add(Services.StringToBitMap(json.getString(""+i)));
                    i++;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



            return bitmaps;
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
            try {
                int id = 0;
                for(Bitmap bitmap : bitmaps){

                    if(id == 0){productImage.setImageBitmap(bitmap);}

                    id++;
                    ImageView imageView = new ImageView(Product.this);
                    imageView.setId(id);
                    imageView.setPadding(8, 8, 8, 8);

                   // Gives the bitmap rounded corners
                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                    roundedBitmapDrawable.setCornerRadius(10);

                    imageView.setImageDrawable(RoundedImageCorners(bitmap));

                   // imageView.setImageBitmap(bitmap);

                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.width = 500;
                    lp.height = 500;

                    imageView.setLayoutParams(lp);

                    linearImageLayout.addView(imageView);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            startPostponedEnterTransition();
        }
    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    new uploadImage().execute(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Product.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            new uploadImage().execute(thumbnail);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class uploadImage extends AsyncTask<Bitmap, Void, Void> {

        @Override
        protected Void doInBackground(Bitmap... bitmaps) {

            Bitmap bitmap = bitmaps[0];

            Services.postAPI("pictures.php?productID=" + productID, bitmap);

            return null;

        }

    }

    public Drawable RoundedImageCorners(Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCornerRadius(30);

        return roundedBitmapDrawable;

    }

}
