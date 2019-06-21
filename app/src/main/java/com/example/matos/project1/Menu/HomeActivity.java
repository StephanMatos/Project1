package com.example.matos.project1.Menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matos.project1.AlertDialogBoxes;
import com.example.matos.project1.AsyncTask.SetupList;
import com.example.matos.project1.R;
import com.example.matos.project1.Scan.ScanActivity;
import com.example.matos.project1.Users.TabSignupFragment;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private long backPressedTime = 0;

    private FragmentProfile fragmentProfile;
    private FragmentListRecent fragmentListRecent;
    private FragmentListFavorites fragmentListFavorites;
    private FragmentSearch fragmentSearch;
    private FragmentHome fragmentHome;


    public static JSONArray favorites;
    public static JSONArray recents;

    private ViewPager viewPager;
    MenuItem prevMenuItem;
    BottomNavigationView bottomNavigationView;

    FloatingActionButton cameraBtn;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cameraBtn = findViewById(R.id.FAB);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });


        bottomNavigationView = findViewById(R.id.navigationView);
        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_search:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_favorites:
                        viewPager.setCurrentItem(2);
                        break;
                   case R.id.nav_recents:
                        viewPager.setCurrentItem(3);
                       break;
                    case R.id.nav_profile:
                        viewPager.setCurrentItem(4);
                        break;
                }

                return false;



            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragmentHome = new FragmentHome();
        fragmentSearch = new FragmentSearch();
        fragmentListRecent = new FragmentListRecent();
        fragmentListFavorites = new FragmentListFavorites();
        fragmentProfile = new FragmentProfile();
        viewPagerAdapter.addFragment(fragmentHome);
        viewPagerAdapter.addFragment(fragmentSearch);
        viewPagerAdapter.addFragment(fragmentListFavorites);
        viewPagerAdapter.addFragment(fragmentListRecent);
        viewPagerAdapter.addFragment(fragmentProfile);
        viewPager.setAdapter(viewPagerAdapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }


    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();

        if (t - backPressedTime > 3000) {    // 3 secs
            backPressedTime = t;
            Toast.makeText(this, "Press back again to close application",
                    Toast.LENGTH_SHORT).show();

        } else {

            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("on resume");
    }


}
