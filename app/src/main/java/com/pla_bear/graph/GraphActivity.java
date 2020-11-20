package com.pla_bear.graph;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.common.graph.Graph;
import com.pla_bear.R;
import com.pla_bear.base.BaseActivity;
import com.pla_bear.base.Commons;
import com.pla_bear.retrofit.RetrofitClient;
import com.pla_bear.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraphActivity extends BaseActivity {
    private RetrofitService service;
    private int year;
    private Pair<Integer, Integer> range;
    private ArrayList<GraphDTO> list;
    private TextView graphTitle;
    private GraphActivity.GraphPagerAdapter adapterViewPager;
    private ViewPager viewPager;
    private GraphHandler handler = new GraphHandler();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        service = RetrofitClient.getApiService(getString(R.string.graph_api_base));

        graphTitle = findViewById(R.id.graph_title);
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

    private final class GraphHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            ChartCreatable fragment = (ChartCreatable) msg.obj;
            fragment.makeChart(list);
        }
    };

    private void setTabLayout() {
        TabLayout tabLayout = findViewById(R.id.graph_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Regional Waste").setIcon(R.drawable.ic_city));
        tabLayout.addTab(tabLayout.newTab().setText("Waste Sorting").setIcon(R.drawable.ic_rubbish));

        viewPager = findViewById(R.id.graph_view_pager);
        adapterViewPager = new GraphPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                ChartCreatable fragment = (ChartCreatable)adapterViewPager.getItem(position);

                Thread thread = new Thread() {
                    public void run() {
                        synchronized (GraphActivity.this) {
                            if (list == null) {
                                try {
                                    GraphActivity.this.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        Message message = handler.obtainMessage();
                        message.obj = fragment;
                        handler.sendMessage(message);
                    }
                };
                thread.start();

                switch(position) {
                    case 0:
                        graphTitle.setText("지역별 플라스틱 배출량");
                        break;
                    case 1:
                        graphTitle.setText("세부 지역별 쓰레기 처리 현황");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void makeRequest() {
        Thread request = new Thread() {
            public void run() {
                String pid = getResources().getString(R.string.graph_pid);
                String userId = getResources().getString(R.string.graph_userid);
                String apiKey = getResources().getString(R.string.graph_key);

                Call<GraphListDTO> call = service.getInfo(pid, year, userId, apiKey);
                call.enqueue(new Callback<GraphListDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<GraphListDTO> call, @NonNull Response<GraphListDTO> response) {
                        if(response.isSuccessful()) {
                            GraphListDTO container = response.body();

                            synchronized (GraphActivity.this) {
                                list = (ArrayList) container.getData();
                                GraphActivity.this.notify();
                            }

                            ChartCreatable fragment = (ChartCreatable)adapterViewPager.getItem(viewPager.getCurrentItem());
                            fragment.makeChart(list);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GraphListDTO> call, @NonNull Throwable t) {
                        Log.e("Retrofit2", "GetInfo Failed." + t.getMessage());
                    }
                });
            }
        };
        request.start();
    }

    public static class GraphPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments = new ArrayList<>();

        public GraphPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            fragments.add(new RegionWasteFragment());
            fragments.add(new WasteSortingFragment());
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}