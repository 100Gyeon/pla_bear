package com.pla_bear.board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.pla_bear.R;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener{
        //정보 공유 게시판
    Button writeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        writeBtn=(Button)findViewById(R.id.toWriteBoardBtn);
    }

    @Override
    public void onClick(View view) {
        if(writeBtn==view){
            Intent intent = new Intent(getApplication(), WriteBoardActivity.class);
            startActivity(intent);
        }
    }
}