package com.pla_bear.quiz;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends BaseActivity {

    private GridView quizGrid;
    public static List<String> topicList = new ArrayList<>();
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firestore = FirebaseFirestore.getInstance();
        topicList.clear();
        firestore.collection("quiz").document("categories")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()) {
                        long count = (long) doc.get("count");
                        for(int i = 1; i <= count; i++){
                            String topicName = doc.getString("C" + i);
                            topicList.add(topicName);
                        }
                        quizGrid = findViewById(R.id.quizGridview);
                        QuizGridAdapter adapter = new QuizGridAdapter(topicList);
                        quizGrid.setAdapter(adapter);
                    } else {
                        Toast.makeText(QuizActivity.this, "퀴즈 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(QuizActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }
}