package com.pla_bear.board.base;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.pla_bear.base.FirebaseBaseFragment;

abstract public class DetailFragment extends FirebaseBaseFragment {
    protected FirebaseRecyclerAdapter<?, ?> adapter;

    @Override
    public void onStart() {
        super.onStart();
        if(adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter != null) {
            adapter.stopListening();
        }
    }
}
