package com.pla_bear.board.base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;

abstract public class WriteActivity extends BaseActivity {

    protected FirebaseDatabase database;
    protected DatabaseReference databaseReference;
    protected FirebaseStorage storage;
    protected StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        storage = FirebaseStorage.getInstance("gs://plabear.appspot.com");
        storageReference = storage.getReference();
    }

    protected void writeToDatabase(String child, BoardDTO boardDTO) {
        databaseReference.child(child).push().setValue(boardDTO);
    }

    public void onSubmit() {
    }
}