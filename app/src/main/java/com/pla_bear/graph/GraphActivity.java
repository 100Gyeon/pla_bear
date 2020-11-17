package com.pla_bear.graph;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;
import com.pla_bear.base.Commons;
import com.pla_bear.retrofit.RetrofitClient;
import com.pla_bear.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraphActivity extends BaseActivity {
    private RetrofitService service;
    private int year;
    private Pair<Integer, Integer> range;
    Call<GraphDTOContainer> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        service = RetrofitClient.getApiService(getString(R.string.graph_api_base));

        range = new Pair<>(2014, 2018);
        year = range.second;

        makeRequest();
        TextView textView = findViewById(R.id.graph_year);

        Button leftButton = findViewById(R.id.graph_left_button);
        leftButton.setOnClickListener(view -> {
            if(year > range.first) {
                year --;
                textView.setText(Integer.toString(year));
                makeRequest();
            } else {
                Commons.showToast(this, "자원순환정보시스템 OpenAPI 가 해당 년도의 정보를 제공하고 있지 않습니다.");
            }
        });

        Button rightButton = findViewById(R.id.graph_right_button);
        rightButton.setOnClickListener(view -> {
            if(year < range.second) {
                year ++;
                textView.setText(Integer.toString(year));
                makeRequest();
            } else {
                Commons.showToast(this, "자원순환정보시스템 OpenAPI 가 해당 년도의 정보를 제공하지 않습니다.");
            }
        });
    }

    private void makeRequest() {
        String pid = getResources().getString(R.string.graph_pid);
        String userId = getResources().getString(R.string.graph_userid);
        String apiKey = getResources().getString(R.string.graph_key);

        call = service.getInfo(pid, year, userId, apiKey);
        call.enqueue(new Callback<GraphDTOContainer>() {
            @Override
            public void onResponse(Call<GraphDTOContainer> call, Response<GraphDTOContainer> response) {
                if(response.isSuccessful()) {
                    GraphDTOContainer container = response.body();
                    List<GraphDTO> list = container.getData();
                    makeBarChart(list);
                }
            }

            @Override
            public void onFailure(Call<GraphDTOContainer> call, Throwable t) {
                Log.e("Retrofit2", "GetInfo Failed." + t.getMessage());
            }
        });
    }

    private void makeBarChart(List<GraphDTO> list) {
        int size = list.size();
        HashMap<String, Float> map = new HashMap<>();
        int pageCount = 7;

        for(int i=0; i < size; i++) {
            GraphDTO graphDTO = list.get(i);
            String city = graphDTO.getCITY_JIDT_NM();
            if(city.equals("전국")) continue;
            float plasKindQty = graphDTO.getCOMB_PLAS_KIND() + graphDTO.getDSTRCT_PLAS_KIND_QTY();

            if(map.containsKey(city)) {
                map.put(city, map.get(city) + plasKindQty);
            } else {
                map.put(city, plasKindQty);
            }
        }

        RegionWasteBarChart barChart = findViewById(R.id.bar_chart1);

        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();

        int y = 0;
        for(Map.Entry<String, Float> entry : map.entrySet()) {
            keys.add(entry.getKey());
            values.add(new BarEntry(y++, entry.getValue()));
        }

        barChart.setXAxis(keys);
        barChart.setMarker(GraphActivity.this);

        barChart.setBarData(values);
        barChart.setVisibleXRangeMaximum(pageCount);
        barChart.moveViewToX(-1);
    }
}