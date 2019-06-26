package com.example.matos.project1.Scan;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.matos.project1.AsyncTask.AsyncSetupList;
import com.example.matos.project1.Products.CreateProduct;
import com.example.matos.project1.Products.Product;
import com.example.matos.project1.SavedValues;
import com.example.matos.project1.Services;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    String barcode;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AsyncSetupList().execute();
    }


    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        // Log.v("tag", rawResult.getText()); // Prints scan results
        // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        //Open activity with barcode
        barcode = rawResult.getText();
        System.out.println("Scanned text is : " + barcode);
        //Toast.makeText(ScanActivity.this, barcode,Toast.LENGTH_LONG).show();

        new CheckBarcode().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }

    @SuppressLint("StaticFieldLeak")
    private class CheckBarcode extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... voids) {

            String data = Services.callAPI("products.php?barcode=" + barcode);
            //Services.postAPI("scans.php?email=" + username);

            return data;
        }

        @Override
        protected void onPostExecute(String data) {

            try {
                JSONArray jsons = new JSONArray(data);

                JSONObject json = new JSONObject("{\"barcode\":\"null\"}");
                if(jsons.length() > 0) {
                    json = jsons.getJSONObject(0);
                }


            if(!json.getString("barcode").equals("null")) {

                new addToScans().execute();

                Intent intent = new Intent(ScanActivity.this, Product.class);
                intent.putExtra("barcode", barcode);
                startActivity(intent);
            } else {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent = new Intent(ScanActivity.this, CreateProduct.class);
                                intent.putExtra("barcode", barcode);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                mScannerView.resumeCameraPreview(ScanActivity.this);
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ScanActivity.this);
                builder.setMessage("This product is not created. Do you want to create it now?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class addToScans extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            String email = SavedValues.getInstance().getEmail();
            Services.postAPI("scans.php?email=" + email + "&barcode=" + barcode);

            return null;
        }
    }

}