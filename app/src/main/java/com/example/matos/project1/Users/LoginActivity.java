package com.example.matos.project1.Users;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;


import com.example.matos.project1.Menu.HomeActivity;
import com.example.matos.project1.R;
import com.example.matos.project1.SavedValues;

import static java.lang.Thread.sleep;

public class LoginActivity extends AppCompatActivity {

    private static LoginActivity loginActivity;
    static Context context;

    // The SectionsPagerAdapte that provide
    // fragments for each of the sections.
    private SectionsPagerAdapter mSectionsPagerAdapter;

    // The ViewPager will host the section contents.
    private ViewPager mViewPager;

    public static LoginActivity getInstance(){
        if(loginActivity == null){
            loginActivity = new LoginActivity();
        }
        return loginActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();
        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity (Login and Signup)
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Setup the TabLayout with the two tabs (Login and Signup)
        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        ImageView help = findViewById(R.id.help_button);

        help.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(LoginActivity.this);

                dialog.setContentView(R.layout.dialogview_help_login);
                dialog.setTitle("help login");

                Button ok_button = dialog.findViewById(R.id.ok_button);

                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

    }

    void goToHome(){

        Intent intent = new Intent(getContext(), HomeActivity.class);
        getContext().startActivity(intent);
    }

    Context getContext(){
        return context;
    }

    /*
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        return rootView;
    }*/

    // The FragmentPagerAdapter returns a fragment corresponding to one of the tabs
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Show the selected tab in
        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            if (position == 0){
                fragment = new TabLoginFragment();
            } else {
                fragment = new TabSignupFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages (Login or Signup
            return 2;
        }
    }


}
