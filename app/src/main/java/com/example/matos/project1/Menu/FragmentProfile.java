package com.example.matos.project1.Menu;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.matos.project1.AlertDialogBoxes;
import com.example.matos.project1.AsyncTask.SetupList;
import com.example.matos.project1.R;
import com.example.matos.project1.SavedValues;
import com.example.matos.project1.Services;
import com.example.matos.project1.Users.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class FragmentProfile extends Fragment  {

    Context context;

    GridLayout gridLayout;
    TextView ratingCount, favoritesCount, totalScans, usernameTextView;

    int[] icons = {R.drawable.ic_account1, R.drawable.ic_account2,R.drawable.ic_account3, R.drawable.ic_account4,R.drawable.ic_account5,R.drawable.ic_account6,R.drawable.ic_account7,
            R.drawable.ic_account8, R.drawable.ic_account9};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        try{
            context = container.getContext();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        usernameTextView = view.findViewById(R.id.usernameTextView);
        ratingCount = view.findViewById(R.id.avg_rate);
        favoritesCount = view.findViewById(R.id.total_favorites);
        totalScans = view.findViewById(R.id.total_scan);

        ImageView changepicture = view.findViewById(R.id.changePicture);
        final ImageView profile_image = view.findViewById(R.id.profile_picture);

        gridLayout= view.findViewById(R.id.mainGrid);

        setSingleEvent(gridLayout);
/*
        LinearLayout favoriteLayout = view.findViewById(R.id.favoriteLayout);
        LinearLayout recentLayout = view.findViewById(R.id.recentLayout);
        LinearLayout logoutLayout = view.findViewById(R.id.logoutLayout);
        ImageView changepicture = view.findViewById(R.id.changePicture);
        final ImageView profile_image = view.findViewById(R.id.profile_picture);


        favoriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductList.class);
                intent.putExtra("type", "Favorites");
                startActivity(intent);
            }
        });

        recentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductList.class);
                intent.putExtra("type", "Recents");
                startActivity(intent);
            }
        });


        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View v) {
                SharedPreferences sp = Objects.requireNonNull(getContext()).getSharedPreferences("CheckboxFile", MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.putBoolean("CheckBox", false);
                e.apply();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });

*/
        changepicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // create a Dialog component
                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.dialogview_profile_pictures);
                dialog.setTitle("Change profile picture");


                GridView gridView = dialog.findViewById(R.id.gridView);
                CostumGridAdapter itemAdapter = new CostumGridAdapter(context, icons);
                gridView.setAdapter(itemAdapter);

                // clickevent on gridview
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        int imageId;
                        imageId = icons[position];
                        Drawable drawable = getResources().getDrawable(imageId);
                        profile_image.setBackground(drawable);
                        dialog.dismiss();

                    }
                });



                dialog.show();
            }
        });

        new getStats().execute();

    }

    private void setSingleEvent(GridLayout gridLayout) {
        for(int i = 0; i < gridLayout.getChildCount(); i++){
            CardView cardView=(CardView)gridLayout.getChildAt(i);
            final int finalI= i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (finalI == 0){

                    } else if (finalI == 1) {

                    } else if (finalI == 2) {

                    } else {
                        SharedPreferences sp = Objects.requireNonNull(getContext()).getSharedPreferences("CheckboxFile", MODE_PRIVATE);
                        SharedPreferences.Editor e = sp.edit();
                        e.putBoolean("CheckBox", false);
                        e.apply();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        AlertDialogBoxes.finnishactivity(getActivity());
                    }

                }
            });
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getStats extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... voids) {

            String username = SavedValues.getInstance().getEmail();
            String data = Services.callAPI("scans.php?email=" + username);

            JSONObject json = new JSONObject();
            try {
                JSONArray jsons = new JSONArray(data);
                json = jsons.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {

                usernameTextView.setText(json.getString("username"));
                totalScans.setText(json.getString("scans"));
                ratingCount.setText(json.getString("ratingCount"));
                favoritesCount.setText(json.getString("favoritesCount"));

            } catch (Exception e) {
                e.printStackTrace();
            }


            startPostponedEnterTransition();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("Home on Profile");
        new SetupList().execute();
    }
}