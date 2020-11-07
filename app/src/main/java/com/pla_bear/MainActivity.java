package com.pla_bear;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.pla_bear.coupon.CouponMainActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.main_drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();

        NavigationView navigationView=findViewById(R.id.main_drawer_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            HashMap<Integer, Class<?>> map = new HashMap<Integer, Class<?>>() {
                {
                    put(R.id.menu_drawer_1, MapActivity.class);
                    put(R.id.menu_drawer_2, CouponMainActivity.class);
                    put(R.id.menu_drawer_3, ChallengeActivity.class);
                    put(R.id.menu_drawer_4, QuizActivity.class);
                    put(R.id.menu_drawer_5, BoardActivity.class);
                    put(R.id.menu_drawer_6, GraphActivity.class);
                    put(R.id.menu_drawer_7, LoginActivity.class);
                }
            };
            int id = item.getItemId();
            drawer.closeDrawers();
            Intent intent = new Intent(MainActivity.this, map.get(id));
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}