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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.pla_bear.R;
import com.pla_bear.base.Commons;
import com.pla_bear.board.base.DetailActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InfoShareDetailActivity extends DetailActivity {
    private RecyclerView recyclerView;
    private List<InfoShareBoardDTO> infoShareBoardDTOs = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private final int MODIFY_CONTENT = 1000;
    private Button info_write_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_share_board);

        recyclerView = findViewById(R.id.info_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);

        final BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);

        info_write_btn = findViewById(R.id.info_write_btn);
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

            if(infoShareBoardDTO.imageUrl != null) {
                Glide.with(holder.itemView.getContext())
                        .load(infoShareBoardDTO.imageUrl)
                        .into(holder.imageView);
            }

            //버튼 클릭 시 삭제
            holder.deleteBtn.setOnClickListener(view -> {
                deleteArticle(position);
            });

            holder.updateBtn.setOnClickListener(view -> {
                Intent intent = new Intent(InfoShareDetailActivity.this, InfoShareModifyActivity.class);
                intent.putExtra("content",infoShareBoardDTO.getContent());
                if(infoShareBoardDTO.imageUrl != null) {
                    intent.putExtra("imageUrl", infoShareBoardDTO.imageUrl);
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
            ImageView imageView;
            TextView nameView;
            TextView contentView;
            ImageButton deleteBtn;
            ImageButton updateBtn;

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
            task.addOnSuccessListener(e -> {
                deleteDatabaseContent(position);
            });
        }
    }

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
            imageUri = Uri.parse(infoShareBoardDTOs.get(position).imageUrl);
            lastSegment = imageUri.getLastPathSegment();
            return storageReference.child(lastSegment).delete();
        } catch(NullPointerException e) {
            return null;
        }
    }
}


