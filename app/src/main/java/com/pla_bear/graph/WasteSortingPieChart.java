package com.pla_bear.graph;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pla_bear.R;

import java.util.ArrayList;

public class WasteSortingPieChart extends PieChart {
    public WasteSortingPieChart(Context context) {
        super(context);
    }

    public WasteSortingPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WasteSortingPieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDescription() {
        Description description = new Description();
        description.setText("자원순환정보시스템 제공");
        description.setTextSize(12);
        description.setYOffset(description.getYOffset() - 15f);
        this.setDescription(description);
    }

    public void setLegend() {
        Legend legend = this.getLegend();
        legend.setTextSize(16f);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }

    public void setPieData(ArrayList<PieEntry> values) {
        PieDataSet dataSet = new PieDataSet(values, "처리");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setSliceSpace(4f);
        dataSet.setSelectionShift(3f);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.WHITE);
        this.setData(data);
    }

    public void setMarker(Context context) {
        ChartMarkerView barGraphMarkerView = new ChartMarkerView(context, R.layout.graph1_marker_view);
        barGraphMarkerView.setChartView(this);
        this.setMarker(barGraphMarkerView);
        this.setDrawMarkers(true);
    }

    @Override
    protected void init() {
        super.init();
        this.setExtraOffsets(5f, 5f, 5f, 8f);
        this.setUsePercentValues(true);
    }
}
