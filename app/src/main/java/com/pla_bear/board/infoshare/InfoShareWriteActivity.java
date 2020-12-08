package com.pla_bear.board.infoshare;

import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pla_bear.R;
import com.pla_bear.board.base.ImageUploadWriteActivity;

import java.io.File;
import java.util.Objects;

public class InfoShareWriteActivity extends ImageUploadWriteActivity {
    private static final int MAX_IMAGE_COUNT = 1;
    protected ImageButton imageButton;
    protected TextView contentView;
    final private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_share_write);

        TextView textView = findViewById(R.id.write_name_textView);
        textView.setText(getString(R.string.board_sir, firebaseUser.getDisplayName()));

        contentView = findViewById(R.id.write_content_textView);
        imageButton = findViewById(R.id.write_image_imageView);
        imageButton.setOnClickListener(view -> {
            if(localImageUri.size() < MAX_IMAGE_COUNT) {
                localSave();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AlertDialog)
                        .setTitle(R.string.warning)
                        .setMessage(R.string.review_max_exceed)
                        .setPositiveButton(R.string.ok, null)
                        .setIcon(R.drawable.ic_warning)
                        .create();
                alertDialog.show();
            }
        });

        Button button1 = findViewById(R.id.store_content); //저장버튼
        button1.setOnClickListener(view -> onSubmit());

        Button button2 = findViewById(R.id.cancel_content);
        button2.setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AlertDialog)
                    .setTitle("취소")
                    .setMessage("글쓰기를 취소하시겠습니까?")
                    .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                        Intent intent=new Intent(InfoShareWriteActivity.this, InfoShareDetailActivity.class);
                        startActivity(intent);
                    })
                    .create();
            alertDialog.show();
        });
    }

    // submit 버튼 클릭시 호출
    public void onSubmit() {
        if(localImageUri.size() > 0) {
            Uri uri = localImageUri.get(0);
            uploadOnServer(getString(R.string.infoshare_database), uri.toString());
        } else {
            submit();
        }
    }

    private void submit() {
        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();

        String content = contentView.getText().toString();
        String imageUri;

        try {
            imageUri = serverImageUri.get(0).toString();
        } catch(IndexOutOfBoundsException e) {
            imageUri = null;
        }

        InfoShareBoardDTO infoShareBoardDTO = new InfoShareBoardDTO(uid, name, content, imageUri);

        onSuccess(infoShareBoardDTO);

        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AlertDialog)
                .setTitle(R.string.success)
                .setMessage(R.string.register_success_message)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    Intent intent=new Intent(InfoShareWriteActivity.this, InfoShareDetailActivity.class);
                    startActivity(intent);
                })
                .create();
        alertDialog.show();
    }

    public void onSuccess(InfoShareBoardDTO infoShareBoardDTO) {
        writeToDatabase(getString(R.string.infoshare_database), infoShareBoardDTO);
    }

    // Firebase 상에 업로드 성공시 호출되도록 설계
    @Override
    public void onUploadComplete(Uri uri) {
        super.onUploadComplete(uri);
        submit();
    }

    // 카메라, 갤러리에서 이미지를 가져온 뒤 호출
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK && intent != null) {
                    int index = localImageUri.size() - 1;
                    File file = new File(Objects.requireNonNull(localImageUri.get(index).getPath()));
                    Glide.with(this)
                            .load(file)
                            .into(imageButton);
                }
                break;
            case REQUEST_EXTERNAL_CONTENT:
                if (resultCode == RESULT_OK && intent != null) {
                    int index = localImageUri.size() - 1;
                    File file = new File(localImageUri.get(index).getPath());
                    Glide.with(this)
                            .load(file)
                            .into(imageButton);
                }
                break;
        }
    }
}