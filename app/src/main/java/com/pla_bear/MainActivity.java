package com.pla_bear;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pla_bear.base.BaseActivity;
import com.pla_bear.base.PointManager;
import com.pla_bear.map.GeoDAO;

public class MainActivity extends BaseActivity {

    static private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    static private final DatabaseReference databaseReference = database.getReference();
    static private DatabaseReference pointReference = databaseReference.child("point");
    final long INTERVAL_TIME = 3000;
    long previousTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GeoDAO.loadData();
        PointManager.load();
    }
    class PointRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.point_list, parent,false);
            return new CustomViewHolder2(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private class CustomViewHolder2 extends RecyclerView.ViewHolder{
        final TextView textView1;
        final TextView textView2;
//        TextView textView3;
//        TextView textView4;
//        ImageView imageView;

        public CustomViewHolder2(@NonNull View itemView) {
            super(itemView);
            textView1=findViewById(R.id.item_textView1); //이름
            textView2=findViewById(R.id.item_textView2); //point 개수

        }
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
}