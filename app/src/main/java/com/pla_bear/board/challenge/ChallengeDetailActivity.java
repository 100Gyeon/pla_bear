package com.pla_bear.board.challenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.pla_bear.R;
import com.pla_bear.base.Commons;
import com.pla_bear.board.base.DetailActivity;

import java.util.Objects;

public class ChallengeDetailActivity extends DetailActivity {
    protected String path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detail);

        RecyclerView recyclerView = findViewById(R.id.challenge_detail_recycler_view);

        Intent intent = getIntent();
        String content = Objects.requireNonNull(intent).getStringExtra("content");

        TextView titleView = findViewById(R.id.challenge_detail_title);
        titleView.setText(content);

        path = getString(R.string.challenge_action_database) + SEPARATOR + Commons.sha256(content);
        Query query = databaseReference.child(path);
        FirebaseRecyclerOptions<ChallengeBoardDTO> options = new FirebaseRecyclerOptions.Builder<ChallengeBoardDTO>()
                .setQuery(query, ChallengeBoardDTO.class)
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new ChallengeDetailActivity.ChallengeListAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    private static class ChallengeListViewHolder extends RecyclerView.ViewHolder {
        final public TextView nameView;
        final public TextView contentView;
        final public ImageView imageView;

        public ChallengeListViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.challenge_item_name);
            contentView = itemView.findViewById(R.id.challenge_item_content);
            imageView = itemView.findViewById(R.id.challenge_image);
        }
    }

    private class ChallengeListAdapter extends FirebaseRecyclerAdapter<ChallengeBoardDTO, ChallengeDetailActivity.ChallengeListViewHolder> {
        public ChallengeListAdapter(@NonNull FirebaseRecyclerOptions<ChallengeBoardDTO> options) {
            super(options);
        }

        @NonNull
        @Override
        public ChallengeDetailActivity.ChallengeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) ChallengeDetailActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.challenge_detail_item, parent, false);
            return new ChallengeDetailActivity.ChallengeListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChallengeDetailActivity.ChallengeListViewHolder holder, int position, @NonNull ChallengeBoardDTO challengeBoardDTO) {
            holder.nameView.setText(challengeBoardDTO.getName() + " 님");
            holder.contentView.setText(challengeBoardDTO.getContent());

            Glide.with(ChallengeDetailActivity.this)
                    .load(challengeBoardDTO.getImageUrl())
                    .into(holder.imageView);
        }
    }
}