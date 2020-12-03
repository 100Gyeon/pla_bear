package com.pla_bear.base;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

abstract public class FirebaseBaseFragment extends Fragment {
    protected FirebaseDatabase database;
    protected DatabaseReference databaseReference;
    protected final char SEPARATOR = '/';

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }
}
