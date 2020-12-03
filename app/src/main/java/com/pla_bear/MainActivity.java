package com.pla_bear;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.ValueEventListener;
import com.pla_bear.base.FirebaseBaseActivity;
import com.pla_bear.base.PointDTO;


import com.pla_bear.base.PointManager;
import com.pla_bear.map.GeoDAO;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

public class MainActivity extends FirebaseBaseActivity {
    final long INTERVAL_TIME = 3000;
    long previousTime = 0;

    final private List<PointDTO> pointDTOs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference pointReference = databaseReference.child("point");
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final PointRecyclerViewAdapter pointRecyclerViewAdapter=new PointRecyclerViewAdapter();
        recyclerView.setAdapter(pointRecyclerViewAdapter);

        GeoDAO.loadData();
        PointManager.load();

        pointReference.orderByChild("point").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PointDTO pointDTO = snapshot.getValue(PointDTO.class);
                    pointDTOs.add(pointDTO);
                }
                Collections.reverse(pointDTOs);
                pointRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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

     class PointRecyclerViewAdapter extends RecyclerView.Adapter<PointViewHolder> {
         @NonNull
         @Override
         public PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             View view = LayoutInflater.from(parent.getContext())
                     .inflate(R.layout.point_list, parent, false);
             return new PointViewHolder(view);
         }

         @Override
         public void onBindViewHolder(@NonNull PointViewHolder holder, int position) {
             holder.ranking.setText(String.valueOf(position + 1));
             holder.textView.setText(pointDTOs.get(position).getName());
             holder.count.setText(String.valueOf(pointDTOs.get(position).getPoint()));
         }

         @Override
         public int getItemCount() {
             return pointDTOs.size();
         }
     }

     static private class PointViewHolder extends RecyclerView.ViewHolder {
        final TextView ranking; //순위
        final TextView textView; //이름
        final TextView count; //개수

        public PointViewHolder(View view) {
            super(view);
            ranking=view.findViewById(R.id.ranking);
            textView=view.findViewById(R.id.item_textView1);
            count=view.findViewById(R.id.item_textView2);
        }
    }
}
