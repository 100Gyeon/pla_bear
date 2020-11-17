package com.pla_bear.graph;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pla_bear.R;

import java.util.ArrayList;

public class RegionWasteBarChart extends BarChart {
    private int pageCount = 6;

    public RegionWasteBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RegionWasteBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RegionWasteBarChart(Context context) {
        super(context);
    }

    public void setDescriptionText(String text) {
        Description description = this.getDescription();
        description.setText(text);
        description.setTextSize(getResources().getDimension(R.dimen.label_text_size));
    }

    public void setXAxis(ArrayList<String> keys) {
        XAxis xAxis = this.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(keys));
        xAxis.setLabelCount(pageCount);
        xAxis.setTextSize(getResources().getDimension(R.dimen.label_text_size));
    }

    public void setMarker(Context context) {
        BarGraphMarkerView barGraphMarkerView = new BarGraphMarkerView(context, R.layout.graph1_marker_view);
        barGraphMarkerView.setChartView(this);
        this.setMarker(barGraphMarkerView);
        this.setDrawMarkers(true);
    }

    public void setBarData(ArrayList<BarEntry> values) {
        BarDataSet barDataSet = new BarDataSet(values, "플라스틱 배출량");
        barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        barDataSet.setDrawValues(false);
        BarData data = new BarData(barDataSet);
        this.setData(data);
    }

    @Override
    protected void init() {
        super.init();

        this.animateY(2000);
        this.setScaleEnabled(false);

        this.setDescriptionText("ton/day");

        this.setExtraBottomOffset(10);
        this.setExtraTopOffset(10);
    }
}
