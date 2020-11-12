package com.pla_bear.board.base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;
import com.pla_bear.base.FirebaseBaseActivity;

abstract public class WriteActivity extends FirebaseBaseActivity {
    protected void writeToDatabase(String child, BoardDTO boardDTO) {
        databaseReference.child(child).push().setValue(boardDTO);
    }

    public void onSubmit() {
    }
}