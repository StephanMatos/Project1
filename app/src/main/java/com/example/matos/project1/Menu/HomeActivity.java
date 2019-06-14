package com.example.matos.project1.Menu;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.matos.project1.AlertDialogBoxes;
import com.example.matos.project1.Menu.FragmentHome;
import com.example.matos.project1.Menu.FragmentProfile;
import com.example.matos.project1.Menu.FragmentSearch;
import com.example.matos.project1.R;
import com.example.matos.project1.Scan.FragmentCamera;
import com.example.matos.project1.Users.ForgotPasswordActivity;
import com.example.matos.project1.Users.LoginActivity;


public class HomeActivity extends AppCompatActivity {
    private long backPressedTime = 0;
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navgationListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();

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
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        selectedFragment = new FragmentHome();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navgationListner =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectedFragment = null;

                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                selectedFragment = new FragmentHome();
                                break;
                            case R.id.nav_search:
                                selectedFragment = new FragmentSearch();
                                break;
                            case R.id.nav_favorites:
                                selectedFragment = new FragmentCamera();
                                break;
                            case R.id.nav_recents:
                                selectedFragment = new FragmentCamera();
                                break;
                            case R.id.nav_profile:
                                selectedFragment = new FragmentProfile();
                                break;

                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                        return true;
                    }
                };





}
