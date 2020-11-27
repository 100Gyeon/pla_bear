package com.pla_bear.quiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pla_bear.R;

public class LevelGridAdapter extends BaseAdapter {

    private final int level_number;

    public LevelGridAdapter(int level_number) {
        this.level_number = level_number;
    }

    @Override
    public int getCount() {
        return level_number;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.level_item_layout, viewGroup, false);
        } else {
            view = convertView;
        }
        ((TextView)view.findViewById(R.id.level_number)).setText(String.valueOf(i+1));
        return view;
    }
}
