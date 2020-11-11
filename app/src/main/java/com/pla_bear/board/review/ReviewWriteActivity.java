package com.pla_bear.board.review;

import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pla_bear.R;
import com.pla_bear.board.base.ImageUploadWriteActivity;

public class ReviewWriteActivity extends ImageUploadWriteActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        ViewGroup viewGroup = findViewById(R.id.image_upload_buttons);

        for(int i=0 ; i < MAX_IMAGE_COUNT; i++) {
            Button button = (Button)viewGroup.getChildAt(i);
            button.setOnClickListener(view -> localSave());
        }

        Button button = findViewById(R.id.review_submit_button);
        button.setOnClickListener(view -> {
            onSubmit();
        });
    }

    @Override
    public void onSubmit() {
        RatingBar ratingBar = findViewById(R.id.review_rating);
        float rating = ratingBar.getRating();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();

        EditText editText = findViewById(R.id.review_content);
        String content = editText.getText().toString();
        ReviewBoardDTO reviewBoardDTO = new ReviewBoardDTO(uid, name, content, rating, localImageUri.toArray(new Uri[3]));

        writeToDatabase("review", reviewBoardDTO);
    }

    @Override
    public void onUploadComplete(Uri uri) {
        super.onUploadComplete(uri);
        // mageUri[imageUri.length] = uri.toString();
    }
}