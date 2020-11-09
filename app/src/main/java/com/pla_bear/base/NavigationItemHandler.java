package com.pla_bear.base;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.pla_bear.board.BoardActivity;
import com.pla_bear.ChallengeActivity;
import com.pla_bear.GraphActivity;
import com.pla_bear.LoginActivity;
import com.pla_bear.map.MapActivity;
import com.pla_bear.QuizActivity;
import com.pla_bear.R;
import com.pla_bear.coupon.CouponMainActivity;

import java.util.HashMap;

public class NavigationItemHandler implements NavigationView.OnNavigationItemSelectedListener {
    Activity activity;

    public NavigationItemHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        DrawerLayout drawer = activity.findViewById(R.id.main_drawer);
        drawer.closeDrawers();
        Intent intent = new Intent(activity, map.get(id));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.startActivity(intent);
        return false;
    }
}
