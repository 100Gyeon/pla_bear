package com.pla_bear.base;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pla_bear.R;

abstract public class FirebaseBaseFragment extends Fragment {
    protected FirebaseDatabase database;
    protected DatabaseReference databaseReference;
    protected FirebaseStorage storage;
    protected StorageReference storageReference;
    protected final char SEPARATOR = '/';

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        storage = FirebaseStorage.getInstance(getString(R.string.firebase_storage));
        storageReference = storage.getReference();
    }
}
