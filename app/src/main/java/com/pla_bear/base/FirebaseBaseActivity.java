package com.pla_bear.base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pla_bear.R;

abstract public class FirebaseBaseActivity extends BaseActivity {
    protected FirebaseDatabase database;
    protected DatabaseReference databaseReference;
    protected FirebaseStorage storage;
    protected StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        storage = FirebaseStorage.getInstance(getString(R.string.firebase_storage));
        storageReference = storage.getReference();
    }
}