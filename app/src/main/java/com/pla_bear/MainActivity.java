package com.pla_bear;

import android.os.Bundle;

import com.pla_bear.base.BaseActivity;
import com.pla_bear.map.GeoDAO;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GeoDAO.loadData();
    }
}