package com.example.matos.project1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.matos.project1.Menu.HomeActivity;
import com.example.matos.project1.Products.ItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;

public class SearchListActivity extends AppCompatActivity {

    ListView listView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        postponeEnterTransition();

        listView = findViewById(R.id.searchListView);
        searchView = findViewById(R.id.searchView);
        searchView.setFocusable(false);
        searchView.setQuery(getIntent().getExtras().getString("searchtext"), false);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String searchtext = searchView.getQuery().toString();

                String username = SavedValues.getInstance().getEmail();
                searchtext = searchtext.replace(" ", "%25");
                String address = "products.php?username=" + username + "&searchtext=" + searchtext;

                new getList().execute(address);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

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
