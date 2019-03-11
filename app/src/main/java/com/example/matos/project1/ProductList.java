package com.example.matos.project1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    ImageView testBtn, testBtn2;
    ListView listView;
    TextView titleTextView;
    ArrayList<JSONObject> jsons = new ArrayList<>();

    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        listView = findViewById(R.id.listView);
        titleTextView = findViewById(R.id.titleTextView);

        type = getIntent().getExtras().getString("type");

        titleTextView.setText(type);

        new setupList().execute();

    }

    private class setupList extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        ArrayList<JSONObject> jsons = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(ProductList.this, "Product List","Loading. Please wait...");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //Get itemfromAPI
            jsons = JSONTest();

            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {

            ItemAdapter itemAdapter = new ItemAdapter(ProductList.this, jsons);
            listView.setAdapter(itemAdapter);
            dialog.dismiss();
        }
    }

    ArrayList<JSONObject> JSONTest(){
        ArrayList<JSONObject> jsons = new ArrayList<>();

        for(int i = 0; i < 7; i++){
            JSONObject json = new JSONObject();
            try {
                json.put("name", "Matos Speciale" + (i+1));

                //DecimalFormat df1 = new DecimalFormat(".#");
                //json.put("rating", df1.format(1.3+i*0.1));
                json.put("rating", 1+i);

                if(i % 2 == 0){
                    json.put("inFavorite", true);
                } else{
                    json.put("inFavorite", false);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsons.add(json);
        }
        return jsons;
    }

    private class APITest extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(Void... voids) {

            String q = "select ScannerDeviceID, ProductName, count(ProductName) as Quantity from scanner_has_product natural join product where ProductName = \"nameA\" group by ProductName";
            System.out.println(q);
            return Services.callAPI(q);

        }

        @Override
        protected void onPostExecute(String response){

            System.out.println("Response from API is: " + response);

        }

    }

}
