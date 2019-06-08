package com.example.matos.project1;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navgationListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navgationListner =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                selectedFragment = new FragmentHome();
                                break;
                            case R.id.nav_search:
                                selectedFragment = new FragmentSearch();
                                break;
                            case R.id.nav_profile:
                                selectedFragment = new FragmentProfile();
                                break;
                            case R.id.nav_camera:
                                selectedFragment = new FragmentCamera();
                                break;
                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                        return true;
                    }
                };





}
