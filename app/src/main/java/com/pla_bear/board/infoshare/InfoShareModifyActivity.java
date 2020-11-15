package com.pla_bear.board.infoshare;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;

public class InfoShareModifyActivity extends InfoShareWriteActivity {
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent.hasExtra("content")) {
            String content = intent.getStringExtra("content");
            contentView.setText(content);
        }

        if(intent.hasExtra("imageUrl")) {
            String imageUrl = intent.getStringExtra("imageUrl");

            if(imageUrl != null) {
                Glide.with(this)
                        .load(imageUrl)
                        .into(imageButton);
            }
        }

        if(intent.hasExtra("key")) {
            key = intent.getStringExtra("key");
        }
    }

    @Override
    public void onSuccess(InfoShareBoardDTO infoShareBoardDTO) {
        String child = "infoshare" + SEPARATOR + key;
        updateToDatabase(child, infoShareBoardDTO);
    }
}
