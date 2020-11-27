package com.pla_bear.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pla_bear.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView question, quest_cnt, timer;
    private Button option1, option2, option3, option4;
    private List<QuestionDTO> questionList;
    int questNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question = findViewById(R.id.question);
        quest_cnt = findViewById(R.id.quest_cnt);
        timer = findViewById(R.id.timer);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        getQuestions();
    }

    private void getQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new QuestionDTO("Question 1", "A", "B", "C", "D", 2));
        questionList.add(new QuestionDTO("Question 2", "E", "F", "G", "H", 2));
        questionList.add(new QuestionDTO("Question 3", "I", "J", "K", "L", 2));
        questionList.add(new QuestionDTO("Question 4", "M", "N", "O", "P", 2));
        questionList.add(new QuestionDTO("Question 5", "Q", "R", "S", "T", 2));

        setQuestions();
    }

    private void setQuestions() {
        timer.setText(String.valueOf(10));
        question.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());
        String start = String.valueOf(1).concat("/"); // 문제 1번부터 시작
        String size = String.valueOf(questionList.size());
        String cnt = start.concat(size);
        quest_cnt.setText(cnt);
        startTimer();
        questNum = 0;
    }

    private void startTimer() {
        CountDownTimer cd = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 10초 동안 1초마다 업데이트 되면서 남은 시간 보여주기
                timer.setText(String.valueOf(millisUntilFinished / 1000 + 1));
            }

            @Override
            public void onFinish() {
                // 0초가 되면 다음 문제로 넘어가기
                timer.setText("0");
                changeQuestion();
            }
        };
        cd.start();
    }

    private void changeQuestion() {
        // 다음 문제로 넘어가는 부분
    }

    @Override
    public void onClick(View v) {
        int select = 0;
        switch(v.getId())
        {
            case R.id.option1:
                select = 1;
                break;
            case R.id.option2:
                select = 2;
                break;
            case R.id.option3:
                select = 3;
                break;
            case R.id.option4:
                select = 4;
                break;
        }
        checkAnswer(select);
    }

    private void checkAnswer(int select) {
        if(select == questionList.get(questNum).getCorrectAnswer())
        {
            // 정답이면

        } else {
            // 정답이 아니면
        }
        changeQuestion();
    }
}