package com.pla_bear.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pla_bear.R;

import java.util.ArrayList;
import java.util.List;

import static com.pla_bear.quiz.LevelActivity.category_name;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore firestore;
    private TextView question, quest_cnt, timer;
    private Button option1, option2, option3, option4;
    private List<QuestionDTO> questionList;
    private int questNum;
    private int level;

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

        firestore = FirebaseFirestore.getInstance();
        level = getIntent().getIntExtra("lv", 1);

        getQuestions();
    }

    private void getQuestions() {
        questionList = new ArrayList<>();
        firestore.collection("quiz").document("c" + String.valueOf(category_name))
                .collection("level" + String.valueOf(level))
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        // 데이터베이스 사용 (질문, 선택지, 답 가져오기)
                        QuerySnapshot questions = task.getResult();
                        for(QueryDocumentSnapshot doc : questions){
                            questionList.add(new QuestionDTO(doc.getString("question"),
                                    doc.getString("a"),
                                    doc.getString("b"),
                                    doc.getString("c"),
                                    doc.getString("d"),
                                    Integer.parseInt(doc.getString("answer"))
                            ));
                        }
                        // 가져온 정보로 화면 구성
                        setQuestions();
                    } else {
                        Toast.makeText(QuestionActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setQuestions() {
        timer.setText(String.valueOf(10));
        question.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());
        String start_cnt = "1/" + questionList.size();
        quest_cnt.setText(start_cnt);
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
        if(questNum < questionList.size()-1) {
            // 다음 문제로 이동
            questNum++;
            playAnimation(question, 0, 0);
            playAnimation(option1, 0, 1);
            playAnimation(option2, 0, 2);
            playAnimation(option3, 0, 3);
            playAnimation(option4, 0, 4);
            String next_cnt = (questNum + 1) + "/" + questionList.size();
            quest_cnt.setText(next_cnt);
            timer.setText(String.valueOf(10));
            startTimer();
        }
        else {
            // 마지막 문제면 점수를 알려주는 결과 화면으로 이동
        }
    }

    private void playAnimation(final View view, final int value, final int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(value == 0) {
                            switch (viewNum)
                            {
                                case 0:
                                    ((TextView)view).setText(questionList.get(questNum).getQuestion());
                                    break;
                                case 1:
                                    ((Button)view).setText(questionList.get(questNum).getOptionA());
                                    break;
                                case 2:
                                    ((Button)view).setText(questionList.get(questNum).getOptionB());
                                    break;
                                case 3:
                                    ((Button)view).setText(questionList.get(questNum).getOptionC());
                                    break;
                                case 4:
                                    ((Button)view).setText(questionList.get(questNum).getOptionD());
                                    break;
                            }

                            if(viewNum != 0){
                                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D7F1FA")));
                            }

                            playAnimation(view, 1, viewNum);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
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