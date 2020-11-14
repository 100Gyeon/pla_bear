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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InfoShareDetailActivity extends DetailActivity {
    private RecyclerView recyclerView;
    private List<InfoShareBoardDTO> infoShareBoardDTOs = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();

    private Button info_write_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_share_board);

        recyclerView = findViewById(R.id.info_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                Collections.reverse(infoShareBoardDTOs); //먼저 쓴 글이 먼저 보이도록
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
            holder.textView.setText(infoShareBoardDTO.name);
            holder.textView2.setText(infoShareBoardDTO.content);

            if (infoShareBoardDTO.imageUrl != null) {
                Glide.with(holder.itemView.getContext())
                        .load(infoShareBoardDTO.imageUrl.get(0))
                        .into(holder.imageView);
            } else {
                Glide.with(holder.itemView.getContext())
                        .load(R.drawable.ic_gallery)
                        .into(holder.imageView);
            }

            //버튼 클릭 시 삭제
            holder.deleteBtn.setOnClickListener(view -> {
                Task<Void> task = deleteImageStorage(position);

                if (task == null) {  // 이미지가 없는 경우
                    deleteDatabaseContent(position);
                } else {
                    task.addOnSuccessListener(aVoid -> {
                        deleteDatabaseContent(position);
                    });
                }
            });

//            holder.updateBtn.setOnClickListener(view -> {
//                if (true) { //글쓴 사람과 수정하려고 클릭한 사람이 같아야 함
//                    Intent intent = new Intent(InfoShareDetailActivity.this, InfoShareWriteActivity.class);
//                    intent.putExtra("forUpdate",3);
//                    intent.putExtra("content",infoShareBoardDTO.getContent());
//                    if(infoShareBoardDTO.imageUrl!=null){
//                        intent.putExtra("imageUrl",infoShareBoardDTO.imageUrl.toString());}
//                    else {
//                        intent.putExtra("imageUrl",0);
//                    }
//                    startActivity(intent);
//                    //  Task<Void> task = deleteImageStorage(position);
//
//                }
//            })
            //업데이트 부분은 더 공부해고 수정해야겠음.
        }

        @Override
        public int getItemCount() {
            return infoShareBoardDTOs.size();
        }

        private Task<Void> deleteDatabaseContent(final int position) {
            DatabaseReference ref = databaseReference.child("infoshare");
            ref = ref.child(uidLists.get(position));
            return ref.removeValue()
                    .addOnSuccessListener(e -> Commons.showToast(InfoShareDetailActivity.this, "삭제가 완료 되었습니다."))
                    .addOnFailureListener(e -> Commons.showToast(InfoShareDetailActivity.this, "삭제 실패"));
        }

        private Task<Void> deleteImageStorage(final int position) {
            StorageReference sRef;
            Uri imageUri;
            String imageName;
            if(infoShareBoardDTOs.get(position).imageUrl!=null) //사진이 database에 url로 없는 경우
                {imageUri = Uri.parse(infoShareBoardDTOs.get(position).imageUrl.get(0)); }
            else{
                imageUri=null;
            }

            if(imageUri!=null){
                imageName = imageUri.getLastPathSegment();

            }else {
                imageName=null;
            }
            if(imageName != null) {
                sRef = storageReference.child(imageName);
                return sRef.delete();
            } else {
                return null;
            }
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            TextView textView2;
            ImageButton deleteBtn;
            ImageButton updateBtn;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.board_image_imageView);
                textView = (TextView) view.findViewById(R.id.board_name_textView); //이름
                textView2 = (TextView) view.findViewById(R.id.board_content_textView); //내용
                deleteBtn = (ImageButton) view.findViewById(R.id.info_delete_btn);
                updateBtn = (ImageButton) view.findViewById(R.id.info_update_btn);
            }
        }
    }
}



