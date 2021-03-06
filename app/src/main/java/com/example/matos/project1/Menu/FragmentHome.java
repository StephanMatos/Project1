package com.example.matos.project1.Menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.matos.project1.AsyncTask.AsyncSetupList;
import com.example.matos.project1.R;

import org.json.JSONArray;


public class FragmentHome extends Fragment {

    public ListView listView;

    public static boolean success = false;
    public static JSONArray jsons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        new AsyncSetupList().execute();
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("Home on resume");
        new AsyncSetupList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }





}
