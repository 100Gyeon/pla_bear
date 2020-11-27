package com.pla_bear.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.pla_bear.board.challenge.ChallengeMainListActivity;
import com.pla_bear.graph.GraphActivity;
import com.pla_bear.auth.LoginActivity;
import com.pla_bear.quiz.QuizActivity;
import com.pla_bear.R;
import com.pla_bear.board.infoshare.InfoShareDetailActivity;
import com.pla_bear.coupon.CouponMainActivity;
import com.pla_bear.map.MapActivity;

import java.util.HashMap;

abstract public class BaseActivity extends AppCompatActivity {
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = drawer.findViewById(R.id.main_navigation_view);

        toggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            HashMap<Integer, Class<?>> map = new HashMap<Integer, Class<?>>() {
                {
                    put(R.id.menu_drawer_1, MapActivity.class);
                    put(R.id.menu_drawer_2, CouponMainActivity.class);
                    put(R.id.menu_drawer_3, ChallengeMainListActivity.class);
                    put(R.id.menu_drawer_4, QuizActivity.class);
                    put(R.id.menu_drawer_5, InfoShareDetailActivity.class);
                    put(R.id.menu_drawer_6, GraphActivity.class);
                    put(R.id.menu_drawer_7, LoginActivity.class);
                }
            };
            int id = item.getItemId();
            DrawerLayout mDrawer = BaseActivity.this.findViewById(R.id.main_drawer);
            mDrawer.closeDrawers();
            Intent intent = new Intent(BaseActivity.this, map.get(id));

            if(map.get(id)==LoginActivity.class){
                FirebaseAuth.getInstance().signOut();
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            BaseActivity.this.startActivity(intent);
            return false;
        });

        FrameLayout frameLayout = drawer.findViewById(R.id.content_frame);
        inflater.inflate(layoutResID, frameLayout);

        super.setContentView(drawer);
    }
}