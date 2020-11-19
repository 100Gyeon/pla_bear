package com.pla_bear.graph;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.data.BarEntry;
import com.pla_bear.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionWasteFragment extends Fragment implements ChartCreatable {
    private Context mContext;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void makeChart(List<GraphDTO> list) {
        int size = list.size();
        HashMap<String, Float> map = new HashMap<>();
        int pageCount = 7;

        for(int i=0; i < size; i++) {
            GraphDTO graphDTO = list.get(i);
            String city = graphDTO.getCITY_JIDT_NM();
            String dataTm = graphDTO.getDATA_TM_NM();

            if(city.equals("전국") || dataTm.equals("발생량")) continue;
            float plasticKindQty = graphDTO.getCOMB_PLAS_KIND() + graphDTO.getDSTRCT_PLAS_KIND_QTY();

            if(map.containsKey(city)) {
                map.put(city, map.get(city) + plasticKindQty);
            } else {
                map.put(city, plasticKindQty);
            }
        }

        RegionWasteBarChart barChart = view.findViewById(R.id.bar_chart1);

        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();

        int y = 0;
        for(Map.Entry<String, Float> entry : map.entrySet()) {
            keys.add(entry.getKey());
            values.add(new BarEntry(y++, entry.getValue()));
        }

        barChart.setXAxis(keys);
        barChart.setMarker(mContext);

        barChart.setBarData(values);
        barChart.setVisibleXRangeMaximum(pageCount);
        barChart.moveViewToX(-1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_region_waste, container, false);
        this.view = view;
        return view;
    }
}
