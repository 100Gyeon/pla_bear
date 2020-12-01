package com.pla_bear.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;

public class LevelActivity extends BaseActivity {

    private FirebaseFirestore firestore;
    public static int category_name;
    private GridView level_grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        category_name = getIntent().getIntExtra("category_name", 1);
        level_grid = findViewById(R.id.level_gridview);
        firestore = FirebaseFirestore.getInstance();
        showLevels();
    }

    private void showLevels(){
        firestore.collection("quiz").document("c" + category_name)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if(doc.exists()) {
                            long level = (long)doc.get("level");
                            LevelGridAdapter adapter = new LevelGridAdapter((int)level);
                            level_grid.setAdapter(adapter);
                        } else {
                            Toast.makeText(LevelActivity.this, "레벨 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(LevelActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LevelActivity.this, QuizActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        LevelActivity.this.finish();
    }

}