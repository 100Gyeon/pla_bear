package com.pla_bear.board.infoshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pla_bear.R;
import com.pla_bear.board.base.ImageUploadWriteActivity;
import com.pla_bear.board.review.ReviewBoardDTO;
import com.pla_bear.board.review.ReviewDetailActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.os.Bundle;

public class InfoShareBoardActivity extends ImageUploadWriteActivity {
    private RecyclerView recyclerView;
    private List<InfoShareBoardDTO>infoShareBoardDTOs=new ArrayList<>();
    private List<String> uidLists=new ArrayList<>();

    private Button info_write_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_share_board);

        database= FirebaseDatabase.getInstance();

        recyclerView=(RecyclerView)findViewById(R.id.info_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final BoardRecyclerViewAdapter boardRecyclerViewAdapter=new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);

        info_write_btn=(Button)findViewById(R.id.info_write_btn);
        info_write_btn.setOnClickListener(view -> {
            Intent intent=new Intent(InfoShareBoardActivity.this, InfoShareWriteActivity.class);
            startActivity(intent);
        });


        database.getReference().child("infoshare").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                infoShareBoardDTOs.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                     InfoShareBoardDTO infoShareBoardDTO=dataSnapshot.getValue(InfoShareBoardDTO.class);
                    infoShareBoardDTOs.add(infoShareBoardDTO);
                }
                Collections.reverse(infoShareBoardDTOs);
                boardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_share_item, parent, false);

            return new CustomViewHolder(view);
        }



        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // ImageDTO imageDTO=imageDTOs.get(position);

            ((CustomViewHolder)holder).textView.setText(infoShareBoardDTOs.get(position).name);
            ((CustomViewHolder)holder).textView2.setText(infoShareBoardDTOs.get(position).content);
            Glide.with(holder.itemView.getContext()).load(infoShareBoardDTOs.get(position).imageUrl.set(0,null)).into(((CustomViewHolder)holder).imageView);


        }

        @Override
        public int getItemCount() {
            return infoShareBoardDTOs.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            TextView textView2;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.board_image_imageView);
                textView = (TextView) view.findViewById(R.id.board_name_textView); //이름
                textView2 = (TextView) view.findViewById(R.id.board_content_textView); //내용
            }
        }
    }

}


