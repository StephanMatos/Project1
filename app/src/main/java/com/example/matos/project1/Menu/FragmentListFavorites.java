package com.example.matos.project1.Menu;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.matos.project1.Products.ItemAdapter;
import com.example.matos.project1.R;
import com.example.matos.project1.SavedValues;
import com.example.matos.project1.Services;

import org.json.JSONArray;
import org.json.JSONException;

import dmax.dialog.SpotsDialog;


public class FragmentListFavorites extends Fragment {

    String type;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            type = bundle.getString("type", "");
        }

        return inflater.inflate(R.layout.fragment_list, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        listView = view.findViewById(R.id.listView);

        TextView t = view.findViewById(R.id.titleTextView);
        t.setText(type);

        new setupList().execute();

    }


    private class setupList extends AsyncTask<Void, Void, Void> {

        JSONArray jsons;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String username = SavedValues.getInstance().getEmail();

            String data;

            data = Services.callAPI("products.php?username=" + username + "&favorites=1");

            try {
                jsons = new JSONArray(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {

            ItemAdapter itemAdapter = new ItemAdapter(getActivity(), jsons);
            listView.setAdapter(itemAdapter);
        }
    }

}

