package com.pla_bear;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.pla_bear.base.BaseActivity;
import com.pla_bear.base.PointManager;

import com.pla_bear.map.GeoDAO;

import java.util.Iterator;


import static com.pla_bear.base.PointManager.userMap;

public class MainActivity extends BaseActivity {
    static public FirebaseDatabase database = FirebaseDatabase.getInstance();
    static public final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    static public DatabaseReference databaseReference = database.getReference();
    static public DatabaseReference pointReference = databaseReference.child("point2");
    final long INTERVAL_TIME = 3000;
    long previousTime = 0;

    private RecyclerView recyclerView;
    public String key;
    public Long value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final PointRecyclerViewAdapter pointRecyclerViewAdapter=new PointRecyclerViewAdapter();
        recyclerView.setAdapter(pointRecyclerViewAdapter);



        GeoDAO.loadData();

        PointManager.load();

    }

    @Override
    public void onBackPressed(){
        long currentTime = System.currentTimeMillis();
        if((currentTime - previousTime) <= INTERVAL_TIME) {
            super.onBackPressed();
        } else {
            previousTime = currentTime;
            Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

     class PointRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.point_list, parent, false);
            return new PointViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            Iterator<String> iterator= userMap.keySet().iterator();
//            while(iterator.hasNext()){
//                String key=iterator.next(); //이름
//                Long value=PointManager.userMap.get(key); //point
//            }
            ((PointViewHolder) holder).ranking.setText(String.valueOf(position + 1));
//            ((PointViewHolder) holder).textView.setText();
//            ((PointViewHolder) holder).count.setText();
    //        ((PointViewHolder) holder).imageView.setImageDrawable(getDrawable(R.drawable.ic_star));
        }

        @Override
        public int getItemCount() {
            return 3; //일단 보여지는 cardview의 개수를 3개로 함.
        }
    }

         private class PointViewHolder extends RecyclerView.ViewHolder {
            TextView ranking; //순위
            TextView textView; //이름
            TextView count; //개수
            ImageView imageView;

            public PointViewHolder(View view) {
                super(view);
                ranking=view.findViewById(R.id.ranking);
                textView=view.findViewById(R.id.item_textView1);
                count=view.findViewById(R.id.item_textView2);
                imageView=view.findViewById(R.id.item_imageView);
            }
        }
    }
