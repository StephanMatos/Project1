package com.example.matos.project1;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {


    // The SectionsPagerAdapte that provide
    // fragments for each of the sections.
    private SectionsPagerAdapter mSectionsPagerAdapter;


    // The ViewPager will host the section contents.
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        return rootView;
    }



    // The FragmentPagerAdapter returns a fragment corresponding to one of the tabs
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
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
            return 3;
        }
    }
}
