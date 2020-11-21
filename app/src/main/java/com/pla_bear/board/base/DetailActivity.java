package com.pla_bear.board.base;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.pla_bear.base.FirebaseBaseActivity;

abstract public class DetailActivity extends FirebaseBaseActivity {
    protected FirebaseRecyclerAdapter<?, ?> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null) {
            adapter.stopListening();
        }
    }
}