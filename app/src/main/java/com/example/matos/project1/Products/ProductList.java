package com.example.matos.project1.Products;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.matos.project1.R;
import com.example.matos.project1.SavedValues;
import com.example.matos.project1.Services;

import org.json.JSONArray;
import org.json.JSONException;

import dmax.dialog.SpotsDialog;

public class ProductList extends AppCompatActivity {

    ListView listView;
    TextView titleTextView;

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

        AlertDialog dialog;
        JSONArray jsons;

        @Override
        protected void onPreExecute() {
            dialog = new SpotsDialog.Builder().setTheme(R.style.loading_dots_theme).setContext(ProductList.this).build();
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String username = SavedValues.getInstance().getEmail();

            String data;
            if(type.equals("Favorites")){
                data = Services.callAPI("products.php?username=" + username + "&favorites=1");
            } else {
                data = Services.callAPI("products.php?username=" + username + "&recents=1");
            }

            try {
                jsons = new JSONArray(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {

            ItemAdapter itemAdapter = new ItemAdapter(ProductList.this, jsons);
            listView.setAdapter(itemAdapter);
            dialog.dismiss();
        }
    }

}
