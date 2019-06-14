package com.example.matos.project1.Menu;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.SharedPreferences;

import com.example.matos.project1.Products.ProductList;
import com.example.matos.project1.R;
import com.example.matos.project1.Users.LoginActivity;

import java.util.Objects;
import java.util.StringJoiner;

import static android.content.Context.MODE_PRIVATE;

public class FragmentProfile extends Fragment  {

    Context context;

    int[] icons = {R.drawable.ic_account1, R.drawable.ic_account2,R.drawable.ic_account3, R.drawable.ic_account4,R.drawable.ic_account5,R.drawable.ic_account6,R.drawable.ic_account7,
            R.drawable.ic_account8, R.drawable.ic_account9};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        context = container.getContext();


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

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



    }

}