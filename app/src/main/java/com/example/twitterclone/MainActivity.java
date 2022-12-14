package com.example.twitterclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.WindowCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    //CircleImageView menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//------------------------------------------------------------
        // customise status bar
//------------------------------------------------------------
        Window window = MainActivity.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.black));


        //initialize homeFragment when Main activity start
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.mainFragment, homeFragment, homeFragment.getTag())
                .commit();


        // navigate through fragment according to options clicked
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                // HomeFragment homeFragment = new HomeFragment();


                                FragmentManager manager = getSupportFragmentManager();
                                manager.beginTransaction()
                                        .replace(R.id.mainFragment, homeFragment, homeFragment.getTag())
                                        .commit();
                                break;
                            case R.id.search:
                                SearchFragment searchFragment = new SearchFragment();
                                manager = getSupportFragmentManager();
                                manager.beginTransaction()
                                        .replace(R.id.mainFragment, searchFragment, searchFragment.getTag())
                                        .commit();
                                break;
                            case R.id.space:
                                SpaceFragment spaceFragment = new SpaceFragment();
                                manager = getSupportFragmentManager();
                                manager.beginTransaction()
                                        .replace(R.id.mainFragment, spaceFragment, spaceFragment.getTag())
                                        .commit();
                                break;
                            case R.id.notif:


                                break;
                            case R.id.message:

                        }
                        return true;
                    }
                });


    }
}