package com.example.matos.project1.Menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matos.project1.R;

import static android.content.Context.MODE_PRIVATE;

public class FragmentHome extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SharedPreferences sp = getContext().getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("key1", "dette er test 11");
        editor.commit();

        System.out.println("-----------------------------------------------------------------" + sp.getString("key1", "33333333333333"));

        editor.putString("key1", "Dette er test 22");
        editor.commit();

        System.out.println("-----------------------------------------------------------------" + sp.getString("key1", "444444444444444"));




        return inflater.inflate(R.layout.fragment_home, container, false);


    }

}
