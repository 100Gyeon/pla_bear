package com.pla_bear.board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;

public class BoardActivity extends BaseActivity implements View.OnClickListener{
        //정보 공유 게시판
    Button writeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        writeBtn = (Button)findViewById(R.id.toWriteBoardBtn);
        writeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==writeBtn){
            Intent intent = new Intent(BoardActivity.this, WriteBoardActivity.class);
            startActivity(intent);
        }
    }
}