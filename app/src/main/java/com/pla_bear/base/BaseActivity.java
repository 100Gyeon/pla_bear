package com.pla_bear.base;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.pla_bear.R;

abstract public class BaseActivity extends AppCompatActivity {
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DrawerLayout drawer = (DrawerLayout)inflater.inflate(R.layout.activity_base, null);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = drawer.findViewById(R.id.main_navigation_view);

        toggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationItemHandler(this));

        FrameLayout frameLayout = drawer.findViewById(R.id.content_frame);
        inflater.inflate(layoutResID, frameLayout);

        super.setContentView(drawer);
    }
}