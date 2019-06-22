package com.example.matos.project1;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.matos.project1.Menu.HomeActivity;
import com.example.matos.project1.Products.ItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;

public class SearchListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        listView = findViewById(R.id.searchListView);
        postponeEnterTransition();

        String address = getIntent().getExtras().getString("address");
        new getList().execute(address);

    }

    @SuppressLint("StaticFieldLeak")
    private class getList extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... strings) {

            String address = strings[0];
            String data = Services.callAPI(address);

            JSONArray jsons = null;

            try {
                jsons = new JSONArray(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsons;
        }

        @Override
        protected void onPostExecute(JSONArray jsons) {
            super.onPostExecute(jsons);
            ItemAdapter itemAdapter = new ItemAdapter(SearchListActivity.this, jsons);
            listView.setAdapter(itemAdapter);
            startPostponedEnterTransition();

        }
    }


}
