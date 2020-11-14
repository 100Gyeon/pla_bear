package com.pla_bear.board.review;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.pla_bear.R;
import com.pla_bear.board.base.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ReviewDetailActivity extends DetailActivity {
    private String path;
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        path = getString(R.string.review_database);
        try {
            Intent intent = getIntent();

            if (intent.hasExtra("placeName")) {
                path += SEPARATOR + intent.getStringExtra("placeName");
            } else {
                path += SEPARATOR + "unknown";
            }
        } catch(NullPointerException e) {
            throw e;
        }

        TabLayout tabLayout = findViewById(R.id.review_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Photo Review").setIcon(R.drawable.ic_photo));
        tabLayout.addTab(tabLayout.newTab().setText("Text Review").setIcon(R.drawable.ic_document));

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapterViewPager = new ReviewDetailPagerAdapter(getSupportFragmentManager());
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

    public class ReviewDetailPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments = new ArrayList<>();

        public ReviewDetailPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            fragments.add(new ImageReviewDetailFragment());
            fragments.add(new TextReviewDetailFragment());
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragments.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("path", ReviewDetailActivity.this.path);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}