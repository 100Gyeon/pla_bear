package com.pla_bear.board.review;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pla_bear.R;
import com.pla_bear.board.base.ImageUploadWriteActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReviewWriteActivity extends ImageUploadWriteActivity {
    private ViewGroup viewGroup;
    private static final int MAX_IMAGE_COUNT = 3;
    private int uploadDoneCount = 0;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        TextView textView = findViewById(R.id.review_nickname);
        textView.setText(firebaseUser.getDisplayName() + " 님");

        EditText editText = findViewById(R.id.review_content);
        editText.setHint(
                "매장이 환경 보호를 실천하는 정도를 리뷰하는 공간입니다.\n\n" +
                "환경 보호와 관련 없는 리뷰를 올리거나\n" +
                "매장을 비방하는 행위는 삼가해주시기 바랍니다.");

        try {
            Intent intent = getIntent();

            path = getString(R.string.review_database);
            if(intent.hasExtra("placeName")) {
                path += SEPARATOR + intent.getStringExtra("placeName");
            } else {
                path += SEPARATOR + "unknown";
            }
        } catch(NullPointerException e) {
            throw e;
        }

        viewGroup = findViewById(R.id.image_upload_buttons);

        for(int i = 0 ; i < MAX_IMAGE_COUNT; i++) {
            ImageButton button = (ImageButton)viewGroup.getChildAt(i);
            button.setOnClickListener(view -> {
                if (localImageUri.size() < MAX_IMAGE_COUNT) {
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
        }

        Button button = findViewById(R.id.review_submit_button);
        button.setOnClickListener(view -> onSubmit());
    }

    // submit 버튼 클릭시 호출
    @Override
    public void onSubmit() {
        if(localImageUri.size() > 0) {
            for(Uri uri : localImageUri) {
                uploadOnServer(path, uri.toString());
            }
        } else {
            path += SEPARATOR + "text";
            submit();
        }
    }

    private void submit() {
        RatingBar ratingBar = findViewById(R.id.review_rating);
        float rating = ratingBar.getRating();

        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();

        EditText editText = findViewById(R.id.review_content);
        String content = editText.getText().toString();

        List<String> imageUri = new ArrayList<>();
        for(Uri uri : serverImageUri) {
            imageUri.add(uri.toString());
        }

        ReviewBoardDTO reviewBoardDTO = new ReviewBoardDTO(uid, name, content, rating, imageUri);

        writeToDatabase(path, reviewBoardDTO);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.success)
                .setMessage(R.string.register_success_message)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> finish())
                .create();
        alertDialog.show();
    }

    // Firebase 상에 업로드 성공시 호출되도록 설계
    @Override
    public void onUploadComplete(Uri uri) {
        super.onUploadComplete(uri);

        uploadDoneCount ++;
        if(uploadDoneCount == localImageUri.size()) {
            path += SEPARATOR + "image";
            submit();
        }
    }

    // 카메라, 갤러리에서 이미지를 가져온 뒤 호출
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case EXTERNAL_CONTENT:
                if (resultCode == RESULT_OK && intent != null) {
                    int index = localImageUri.size() - 1;
                    ImageButton imageButton = (ImageButton)viewGroup.getChildAt(index);

                    File file = new File(localImageUri.get(index).getPath());
                    Glide.with(this)
                            .load(file)
                            .into(imageButton);
                }
                break;
        }
    }
}