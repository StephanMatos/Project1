package com.example.matos.project1;

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

public class FragmentProfile extends Fragment  {

    Context context;




    int[] icons = {R.drawable.ic_account1, R.drawable.ic_account2,R.drawable.ic_account3, R.drawable.ic_account4};

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
        ImageView changepicture = view.findViewById(R.id.changePicture);
        final ImageView profile_image = view.findViewById(R.id.profile_picture);


                favoriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductList.class);
                intent.putExtra("type", "favorites");
                startActivity(intent);
            }
        });

        recentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductList.class);
                intent.putExtra("type", "recents");
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