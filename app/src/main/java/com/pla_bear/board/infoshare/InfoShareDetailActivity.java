package com.pla_bear.board.infoshare;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pla_bear.R;
import com.pla_bear.base.Commons;
import com.pla_bear.board.base.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class InfoShareDetailActivity extends DetailActivity {
    private final List<InfoShareBoardDTO> infoShareBoardDTOs = new ArrayList<>();
    private final List<String> uidLists = new ArrayList<>();
    private final int MODIFY_CONTENT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_share_board);

        RecyclerView recyclerView = findViewById(R.id.info_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);

        final BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);

        FloatingActionButton info_write_btn = findViewById(R.id.info_write_btn);
        info_write_btn.setOnClickListener(view -> {
            Intent intent = new Intent(InfoShareDetailActivity.this, InfoShareWriteActivity.class);
            startActivity(intent);
        });  //클릭하면 infoShareWriteActivity로 전환


        databaseReference.child("infoshare").addValueEventListener(new ValueEventListener() { //글을 쓸 때 infoshare로 저장
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { //정보가 추가될 때 함수 호출
                infoShareBoardDTOs.clear();
                uidLists.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    InfoShareBoardDTO infoShareBoardDTO = dataSnapshot.getValue(InfoShareBoardDTO.class);
                    infoShareBoardDTOs.add(infoShareBoardDTO); //새로운 DTO를 넣어줌
                    String uidKey = dataSnapshot.getKey();
                    uidLists.add(uidKey);
                }
                boardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<BoardRecyclerViewAdapter.CustomViewHolder> {
        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_share_item, parent, false);
            //info_share_item을 통해서 recyclerview에 넣어주기
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            InfoShareBoardDTO infoShareBoardDTO = infoShareBoardDTOs.get(position);
            holder.nameView.setText(infoShareBoardDTO.name);
            holder.contentView.setText(infoShareBoardDTO.content);

            if(infoShareBoardDTO.getImageUrl() != null) {
                Glide.with(holder.itemView.getContext())
                        .load(infoShareBoardDTO.getImageUrl())
                        .into(holder.imageView);
            }

            if(infoShareBoardDTO.getUid().equals(FirebaseAuth.getInstance().getUid())) {
                holder.deleteBtn.setVisibility(View.VISIBLE);
                holder.updateBtn.setVisibility(View.VISIBLE);
            } else {
                holder.deleteBtn.setVisibility(View.GONE);
                holder.updateBtn.setVisibility(View.GONE);
            }

            //버튼 클릭 시 삭제
            holder.deleteBtn.setOnClickListener(view -> {
                deleteArticle(position);
            });

            holder.updateBtn.setOnClickListener(view -> {
                Intent intent = new Intent(InfoShareDetailActivity.this, InfoShareModifyActivity.class);
                intent.putExtra("content", infoShareBoardDTO.getContent());
                if (infoShareBoardDTO.getImageUrl() != null) {
                    intent.putExtra("imageUrl", infoShareBoardDTO.getImageUrl());
                }
                intent.putExtra("key", uidLists.get(position));
                startActivityForResult(intent, MODIFY_CONTENT);
            });
        }

        @Override
        public int getItemCount() {
            return infoShareBoardDTOs.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            final ImageView imageView;
            final TextView nameView;
            final TextView contentView;
            final ImageButton deleteBtn;
            final ImageButton updateBtn;

            public CustomViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.board_image_imageView);
                nameView = view.findViewById(R.id.board_name_textView); //이름
                contentView = view.findViewById(R.id.board_content_textView); //내용
                deleteBtn = view.findViewById(R.id.info_delete_btn);
                updateBtn = view.findViewById(R.id.info_update_btn);
            }
        }
    }

    private void deleteArticle(int position) {
        Task<Void> task = deleteImageStorage(position);

        if (task == null) {  // 이미지가 없는 경우
            deleteDatabaseContent(position);
        } else {
            task.addOnSuccessListener(e -> deleteDatabaseContent(position));
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    private Task<Void> deleteDatabaseContent(final int position) {
        DatabaseReference ref = databaseReference.child("infoshare");
        ref = ref.child(uidLists.get(position));
        return ref.removeValue()
                .addOnSuccessListener(e -> Commons.showToast(InfoShareDetailActivity.this, "삭제가 완료 되었습니다."))
                .addOnFailureListener(e -> Commons.showToast(InfoShareDetailActivity.this, "삭제 실패"));
    }

    private Task<Void> deleteImageStorage(final int position) {
        Uri imageUri;
        String lastSegment;

        try {
            imageUri = Uri.parse(infoShareBoardDTOs.get(position).getImageUrl());
            lastSegment = imageUri.getLastPathSegment();
            return storageReference.child(lastSegment).delete();
        } catch(NullPointerException e) {
            return null;
        }
    }
}



