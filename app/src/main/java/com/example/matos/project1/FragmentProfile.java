package com.example.matos.project1;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FragmentProfile extends Fragment{

    Context context;

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

        changepicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // create a Dialog component
                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.dialogview_profile_pictures);
                dialog.setTitle("Change profile picture");


                dialog.show();
            }
        });



    }


}