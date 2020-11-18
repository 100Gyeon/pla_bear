package com.pla_bear.graph;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;
import com.google.common.graph.Graph;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;
import com.pla_bear.base.Commons;
import com.pla_bear.board.review.ImageReviewDetailFragment;
import com.pla_bear.board.review.ReviewDetailActivity;
import com.pla_bear.board.review.TextReviewDetailFragment;
import com.pla_bear.retrofit.RetrofitClient;
import com.pla_bear.retrofit.RetrofitService;

import java.io.IOException;
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
    private ArrayList<GraphDTO> list;
    Call<GraphDTOContainer> call;
    GraphActivity.GraphPagerAdapter adapterViewPager;

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
                Commons.showToast(this, "자원순환정보시스템 OpenAPI 가 해당 년도의 정보를 제공하지 않습니다.");
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

        setTabLayout();
    }

    private void setTabLayout() {
        TabLayout tabLayout = findViewById(R.id.graph_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Regional Waste").setIcon(R.drawable.ic_city));

        ViewPager viewPager = findViewById(R.id.graph_view_pager);
        adapterViewPager = new GraphActivity.GraphPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
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
                    list = (ArrayList) container.getData();
                    RegionWasteFragment fragment = (RegionWasteFragment)adapterViewPager.getCurrentFragment();
                    fragment.makeBarChart(list);
                }
            }

            @Override
            public void onFailure(Call<GraphDTOContainer> call, Throwable t) {
                Log.e("Retrofit2", "GetInfo Failed." + t.getMessage());
            }
        });
    }

    static public class GraphPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments = new ArrayList<>();
        Fragment currentFragment;

        public Fragment getCurrentFragment() {
            return currentFragment;
        }

        public GraphPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            fragments.add(new RegionWasteFragment());
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            currentFragment = fragments.get(position);
            return currentFragment;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}