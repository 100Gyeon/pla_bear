package com.pla_bear.graph;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.pla_bear.R;

import java.text.DecimalFormat;

public class ChartMarkerView extends MarkerView {
    private final TextView tvContent;

    public ChartMarkerView(Context context) {
        this(context, R.layout.graph_marker_view);
    }

    public ChartMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        DecimalFormat formatter = new DecimalFormat("#,##0.##");

        tvContent.setText(getContext().getString(R.string.R_string_emissions, formatter.format(e.getY())));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2f ), -getHeight());
    }
}
