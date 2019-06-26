package com.example.matos.project1.AsyncTask;

import android.os.AsyncTask;
import com.example.matos.project1.Menu.HomeActivity;
import com.example.matos.project1.SavedValues;
import com.example.matos.project1.Services;

import org.json.JSONArray;
import org.json.JSONException;

public class AsyncSetupList extends AsyncTask<Void, Void, Void> {

    JSONArray jsons;

    @Override
    protected Void doInBackground(Void... voids) {
        System.out.println("new setupList");

        String username = SavedValues.getInstance().getEmail();

        String data;
        data = Services.callAPI("products.php?username=" + username + "&favorites=1");

        try {
            jsons = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HomeActivity.favorites = jsons;

        data = Services.callAPI("products.php?username=" + username + "&recents=1");
        try {
            jsons = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HomeActivity.recents = jsons;
        return null;
    }





}