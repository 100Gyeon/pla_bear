package com.pla_bear.quiz;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pla_bear.R;

import java.util.List;

public class QuizGridAdapter extends BaseAdapter {
    private final List<String> quizList;

    public QuizGridAdapter(List<String> quizList) {
        this.quizList = quizList;
    }

    @Override
    public int getCount() {
        return quizList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;

        if(convertView == null)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item_layout,parent,false);
        }
        else
        {
            view = convertView;
        }

        view.setOnClickListener(v -> {
            Intent intent = new Intent(parent.getContext(), LevelActivity.class);
            intent.putExtra("categories", quizList.get(position));
            intent.putExtra("category_name", position + 1);
            parent.getContext().startActivity(intent);
        });

        ((TextView)view.findViewById(R.id.quizName)).setText(quizList.get(position));
        return view;
    }
}
