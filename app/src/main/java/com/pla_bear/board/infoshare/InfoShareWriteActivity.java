package com.pla_bear.board.infoshare;

import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pla_bear.R;
import com.pla_bear.board.base.ImageUploadWriteActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InfoShareWriteActivity extends ImageUploadWriteActivity {
    private static final int MAX_IMAGE_COUNT = 1;
    private ImageButton imageButton;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_board);

        TextView textView = findViewById(R.id.write_name_textView);
        textView.setText(firebaseUser.getDisplayName() + " 님");


        imageButton = findViewById(R.id.write_image_imageView);
        imageButton.setOnClickListener(view -> {
            if(localImageUri.size() < MAX_IMAGE_COUNT) {
                localSave();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.warning)
                        .setMessage(R.string.review_max_exceed)
                        .setPositiveButton(R.string.ok, null)
                        .setIcon(R.drawable.warning_icon)
                        .create();
                alertDialog.show();
            }
        });

        Button button = findViewById(R.id.store_content);
        button.setOnClickListener(view -> onSubmit());
    }

    // submit 버튼 클릭시 호출
    @Override
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

        EditText editText = findViewById(R.id.write_content_textView);
        String content = editText.getText().toString();

        List<String> imageUri = new ArrayList<>();
        for(Uri uri : serverImageUri) {
            imageUri.add(uri.toString());
        }

        InfoShareBoardDTO infoShareBoardDTO = new InfoShareBoardDTO(uid, name, content, imageUri);

        writeToDatabase(getString(R.string.infoshare_database), infoShareBoardDTO);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.success)
                .setMessage(R.string.register_success_message)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> finish())
                .create();
        alertDialog.show();

        Intent intent=new Intent(InfoShareWriteActivity.this, InfoShareDetailActivity.class);
        startActivity(intent);
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
            case EXTERNAL_CONTENT:
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