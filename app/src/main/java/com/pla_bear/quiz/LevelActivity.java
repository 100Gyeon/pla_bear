package com.pla_bear.quiz;

import android.os.Bundle;
import android.widget.GridView;

import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;

public class LevelActivity extends BaseActivity {

    private GridView level_grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        level_grid = findViewById(R.id.level_gridview);
        LevelGridAdapter adapter = new LevelGridAdapter(3);
        level_grid.setAdapter(adapter);
    }
}