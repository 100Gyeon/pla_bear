package com.pla_bear.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.pla_bear.MainActivity;
import com.pla_bear.R;
import com.pla_bear.base.PointManager;

import static com.pla_bear.base.PointManager.POINT_QUIZ;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 퀴즈 결과를 보여주는 텍스트뷰
        TextView score = findViewById(R.id.score);
        String result = getIntent().getStringExtra("SCORE");
        score.setText(result);

        // 80점 이상이면 포인트 +1
        int num = getIntent().getIntExtra("NUM", 0);
        if(num >= 80) {
            PointManager.addPoint(POINT_QUIZ);
        }

        // 퀴즈 주제 선택 화면으로 돌아가는 버튼
        Button quiz = findViewById(R.id.quiz);
        quiz.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ResultActivity.this.finish();
        });

        // 메인 액티비티 화면으로 돌아가는 버튼
        Button home = findViewById(R.id.home);
        home.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ResultActivity.this.finish();
        });
    }
}