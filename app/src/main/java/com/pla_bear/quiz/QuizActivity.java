package com.pla_bear.quiz;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuizActivity extends BaseActivity {

    private GridView quizGrid;
    final public static List<String> topicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        topicList.clear();
        firestore.collection("quiz").document("categories")
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if(doc.exists()) {
                            @SuppressWarnings("ConstantConditions") long count = (long) doc.get("count");
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
                        Toast.makeText(QuizActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }
}