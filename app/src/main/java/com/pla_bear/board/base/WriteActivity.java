package com.pla_bear.board.base;

import com.pla_bear.base.FirebaseBaseActivity;

abstract public class WriteActivity extends FirebaseBaseActivity {
    protected void writeToDatabase(String child, BoardDTO boardDTO) {
        databaseReference.child(child).push().setValue(boardDTO);
    }

    protected void updateToDatabase(String child, BoardDTO boardDTO) {
        databaseReference.child(child).setValue(boardDTO);
    }

    public void onSubmit() {
    }
}