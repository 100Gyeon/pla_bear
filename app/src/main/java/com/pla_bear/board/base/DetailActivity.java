package com.pla_bear.board.base;

import android.os.Bundle;

import com.pla_bear.R;
import com.pla_bear.base.FirebaseBaseActivity;

abstract public class DetailActivity extends FirebaseBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}