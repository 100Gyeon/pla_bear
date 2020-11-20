package com.pla_bear.graph;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieEntry;
import com.pla_bear.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WasteSortingFragment extends Fragment implements ChartCreatable {
    private Context mContext;
    private View view;
    private HashMap<String, HashMap<String, HashMap<String, Pair<Float, Float>>>> map;

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
        HashMap<String, HashMap<String, Pair<Float, Float>>> cityMap;
        HashMap<String, Pair<Float, Float>> ctsMap;

        map = new HashMap<>();
        for(int i=0; i < size; i++) {
            GraphDTO graphDTO = list.get(i);
            String city = graphDTO.getCITY_JIDT_NM();
            String cts = graphDTO.getCTS_JIDT_NM();
            String dataTm = graphDTO.getDATA_TM_NM();

            if(city.equals("전국") || dataTm.equals("발생량")) continue;

            if(!map.containsKey(city)) {
                cityMap = new HashMap<>();
                map.put(city, cityMap);
            }

            cityMap = map.get(city);

            if(!cityMap.containsKey(cts)) {
                 ctsMap = new HashMap<>();
                 cityMap.put(cts, ctsMap);
            }

            ctsMap = cityMap.get(cts);
            ctsMap.put(dataTm, new Pair<>(graphDTO.getCOMB_PLAS_KIND(), graphDTO.getDSTRCT_PLAS_KIND_QTY()));
        }

        List<String> cityList = new ArrayList<>(map.keySet());
        Spinner citySpinner = view.findViewById(R.id.city_spinner);
        citySpinner.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, cityList));
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View item, int position, long l) {
                onCityItemSelected(cityList, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void onCityItemSelected(List<String> cityList, int position) {
        HashMap<String, HashMap<String, Pair<Float, Float>>> cityMap = map.get(cityList.get(position));
        List<String> ctsList = new ArrayList<>(cityMap.keySet());
        Spinner ctsSpinner = view.findViewById(R.id.cts_spinner);
        ctsSpinner.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, ctsList));
        ctsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int innerPos, long l) {
                 makePieChart(Objects.requireNonNull(cityMap.get(ctsList.get(innerPos))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void makePieChart(HashMap<String, Pair<Float, Float>> ctsMap) {
        WasteSortingPieChart pieChart = view.findViewById(R.id.waste_sorting_pie_chart);
        ArrayList<PieEntry> values = new ArrayList<>();

        for(Map.Entry<String, Pair<Float, Float>> entry : ctsMap.entrySet()) {
            Pair<Float, Float> pair = entry.getValue();
            float value = pair.first + pair.second;
            if(value != 0) {
                values.add(new PieEntry(value, entry.getKey()));
            }
        }

        pieChart.setLegend();
        pieChart.setMarker(mContext);
        pieChart.setDescription();
        pieChart.animateY(1000, Easing.EaseInOutCubic);
        pieChart.setPieData(values);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waste_sorting, container, false);
        this.view = view;
        return view;
    }
}
