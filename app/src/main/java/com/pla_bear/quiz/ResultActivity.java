package com.pla_bear.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.pla_bear.MainActivity;
import com.pla_bear.R;

public class ResultActivity extends AppCompatActivity {

    private TextView score; // 퀴즈 결과를 보여주는 텍스트뷰
    private Button quiz; // 퀴즈 주제 선택 화면으로 돌아가는 버튼
    private Button home; // 메인 액티비티 화면으로 돌아가는 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        score = findViewById(R.id.score);
        String result = getIntent().getStringExtra("SCORE");
        score.setText(result);

        quiz = findViewById(R.id.quiz);
        quiz.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ResultActivity.this.finish();
        });

        home = findViewById(R.id.home);
        home.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ResultActivity.this.finish();
        });
    }
}