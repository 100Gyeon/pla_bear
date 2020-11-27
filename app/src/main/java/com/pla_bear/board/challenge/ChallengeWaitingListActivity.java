package com.pla_bear.board.challenge;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.pla_bear.R;
import com.pla_bear.board.base.DetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ChallengeWaitingListActivity extends DetailActivity {
    protected String path;
    final private int THRESHOLD = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_waiting_list);

        RecyclerView recyclerView = findViewById(R.id.challenge_waiting_list_view);

        path = getString(R.string.challenge_create_database);
        Query query = databaseReference.child(path);
        FirebaseRecyclerOptions<ChallengeDTO> options = new FirebaseRecyclerOptions.Builder<ChallengeDTO>()
                .setQuery(query, ChallengeDTO.class)
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        layoutManager.setReverseLayout(false);
        adapter = new ChallengeWaitingListActivity.ChallengeListAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    private static class ChallengeListViewHolder extends RecyclerView.ViewHolder {
        final public TextView nameView;
        final public TextView contentView;
        final public ImageButton likesView;
        final public TextView likesCountView;

        public ChallengeListViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.challenge_item_name);
            contentView = itemView.findViewById(R.id.challenge_item_content);
            likesView = itemView.findViewById(R.id.challenge_image_button);
            likesCountView = itemView.findViewById(R.id.challenge_likes_count);
        }
    }

    private class ChallengeListAdapter extends FirebaseRecyclerAdapter<ChallengeDTO, ChallengeWaitingListActivity.ChallengeListViewHolder> {
        public ChallengeListAdapter(@NonNull FirebaseRecyclerOptions<ChallengeDTO> options) {
            super(options);
        }

        @NonNull
        @Override
        public ChallengeWaitingListActivity.ChallengeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) ChallengeWaitingListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.challenge_item, parent, false);
            return new ChallengeWaitingListActivity.ChallengeListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChallengeWaitingListActivity.ChallengeListViewHolder holder, int position, @NonNull ChallengeDTO challengeDTO) {
            ArrayList<String> likes = challengeDTO.getLikes();

            holder.nameView.setText(getString(R.string.sir, challengeDTO.getName()));
            holder.contentView.setText(challengeDTO.getContent());

            holder.likesView.setOnClickListener(view -> {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                ArrayList<String> list = likes;
                Map<String, Object> childUpdates = new HashMap<>();
                assert firebaseUser != null;
                String uid = firebaseUser.getUid();

                if(list == null) {
                    list = new ArrayList<>();
                }

                if(list.contains(uid)) {
                    list.remove(uid);
                    holder.likesView.setImageResource(R.drawable.ic_empty_heart);
                } else {
                    list.add(uid);
                    holder.likesView.setImageResource(R.drawable.ic_fill_heart);
                }

                childUpdates.put("likes", list);

                if(list.size() >= THRESHOLD) {
                    String child = getString(R.string.challenge_submit_database);
                    databaseReference.child(child).push().setValue(challengeDTO);
                    this.getRef(position).removeValue();
                } else {
                    this.getRef(position).updateChildren(childUpdates);
                }
            });

            int count;

            if(likes == null) {
                count = 0;
                holder.likesView.setImageResource(R.drawable.ic_empty_heart);
            } else {
                count = likes.size();
                holder.likesView.setImageResource(R.drawable.ic_fill_heart);
            }
            holder.likesCountView.setText(String.format(Locale.KOREAN, "%d", count));
        }
    }
}