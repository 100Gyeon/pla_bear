package com.pla_bear.board.base;

import android.os.Bundle;

import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;

abstract public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}