package com.example.matos.project1.Menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.matos.project1.FAB_Float_on_Scroll;
import com.example.matos.project1.Products.ItemAdapter;
import com.example.matos.project1.R;
import com.example.matos.project1.SavedValues;
import com.example.matos.project1.Services;


import org.json.JSONArray;
import org.json.JSONException;

import dmax.dialog.SpotsDialog;


public class FragmentListFavorites extends Fragment {

    String type;
    public ListView listView;
    FloatingActionButton cameraBtn;

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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        listView = view.findViewById(R.id.listView);
        cameraBtn = getActivity().findViewById(R.id.FAB);
        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), HomeActivity.favorites);
        listView.setAdapter(itemAdapter);



        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int lastItem = firstVisibleItem + visibleItemCount;

                if (lastItem == totalItemCount) {
                    cameraBtn.hide();
                }else{
                    cameraBtn.show();
                }


            }
        });


    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("on resume favorites");
        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), HomeActivity.favorites);
        listView.setAdapter(itemAdapter);
    }
}

