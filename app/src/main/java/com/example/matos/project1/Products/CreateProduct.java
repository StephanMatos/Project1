package com.example.matos.project1.Products;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matos.project1.R;
import com.example.matos.project1.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateProduct extends AppCompatActivity {

    private int GALLERY = 1, CAMERA = 2;
    String barcode;
    Bitmap bitmapProductImage;

    ImageView productImageView;
    EditText productNameET, productDescriptionET, productPriceET;
    Spinner productStateSpinner;
    CheckBox ovenCheckBox, stoveCheckBox, microwaveCheckBox, hotWaterCheckBox;
    Button createProductBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        barcode = getIntent().getExtras().getString("barcode");


        productImageView = findViewById(R.id.productImageView);
        productNameET = findViewById(R.id.productNameET);
        productDescriptionET = findViewById(R.id.productDescriptionET);
        productPriceET = findViewById(R.id.productPriceET);
        productStateSpinner = findViewById(R.id.productStateSpinner);
        ovenCheckBox = findViewById(R.id.ovenCheckBox);
        stoveCheckBox = findViewById(R.id.stoveCheckBox);
        microwaveCheckBox = findViewById(R.id.microwaveCheckBox);
        hotWaterCheckBox = findViewById(R.id.hotWaterCheckBox);
        createProductBtn = findViewById(R.id.createProductBtn);


        productImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });


        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("Frozen");
        spinnerList.add("Fresh");
        spinnerList.add("Ready");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(CreateProduct.this, android.R.layout.simple_spinner_item, spinnerList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productStateSpinner.setAdapter(dataAdapter);

        createProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreateProductAsync().execute();
            }
        });

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
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    bitmapProductImage = bitmap;
                    productImageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateProduct.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            bitmapProductImage = thumbnail;
            productImageView.setImageBitmap(thumbnail);
        }
    }

    public class CreateProductAsync extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String address = "products.php?barcode=" + barcode
                    + "&productname=" + productNameET.getText().toString()
                    + "&productdescription=" + productDescriptionET.getText().toString()
                    + "&price=" + productPriceET.getText().toString()
                    + "&grill=" + (hotWaterCheckBox.isChecked() ? 1 : 0)
                    + "&ovn=" + (ovenCheckBox.isChecked() ? 1 : 0)
                    + "&komfur=" + (stoveCheckBox.isChecked() ? 1 : 0)
                    + "&mikroovn=" + (microwaveCheckBox.isChecked() ? 1 : 0)
                    + "&andet=" + 0
                    + "&state=" + productStateSpinner.getSelectedItem().toString();
            Services.postAPI(address, bitmapProductImage);
            return null;
        }
    }
}
