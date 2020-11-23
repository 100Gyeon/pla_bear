package com.pla_bear.board.challenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pla_bear.R;
import com.pla_bear.board.base.ImageUploadWriteActivity;
import com.pla_bear.board.base.WriteActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class ChallengeCreateActivity extends WriteActivity {
    protected TextView contentView;
    final private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_write);

        contentView = findViewById(R.id.challenge_content);

        Button button = findViewById(R.id.store_content); //저장버튼
        button.setOnClickListener(view -> onSubmit());

        button = findViewById(R.id.cancel_content);
        button.setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("취소")
                    .setMessage("글 쓰기를 취소하시겠습니까?")
                    .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                        finish();
                    })
                    .create();
            alertDialog.show();
        });
    }

    // submit 버튼 클릭시 호출
    @Override
    public void onSubmit() {
        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();
        String content = contentView.getText().toString();

        ChallengeDTO challengeDTO = new ChallengeDTO(uid, name, content, new ArrayList<>());

        writeToDatabase(getString(R.string.challenge_create_database), challengeDTO);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.success)
                .setMessage(R.string.register_success_message)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    finish();
                })
                .create();
        alertDialog.show();
    }
}