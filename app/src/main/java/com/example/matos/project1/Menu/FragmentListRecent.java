package com.example.matos.project1.Menu;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.matos.project1.Products.ItemAdapter;
import com.example.matos.project1.R;
import com.example.matos.project1.SavedValues;
import com.example.matos.project1.Services;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Objects;

import dmax.dialog.SpotsDialog;


public class FragmentListRecent extends Fragment {

    String type;
    ListView listView;
    public ItemAdapter itemAdapter;
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
    public void onViewCreated(View view, Bundle savedInstanceState) {

        listView = view.findViewById(R.id.listView);
        itemAdapter = new ItemAdapter(getActivity(), HomeActivity.recents);
        listView.setAdapter(itemAdapter);
        cameraBtn = Objects.requireNonNull(getActivity()).findViewById(R.id.FAB);




        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
         @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int lastItem = firstVisibleItem + visibleItemCount;

                if (lastItem == totalItemCount && firstVisibleItem > 0) {
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
        System.out.println("on resume recents");
        itemAdapter = new ItemAdapter(getActivity(), HomeActivity.recents);
        listView.setAdapter(itemAdapter);
    }
}
