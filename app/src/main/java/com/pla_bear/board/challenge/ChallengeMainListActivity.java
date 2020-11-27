package com.pla_bear.board.challenge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.pla_bear.R;
import com.pla_bear.board.base.DetailActivity;

public class ChallengeMainListActivity extends DetailActivity {
    protected String path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_main_list);

        RecyclerView recyclerView = findViewById(R.id.challenge_main_recycler);

        path = getString(R.string.challenge_submit_database);
        Query query = databaseReference.child(path);
        FirebaseRecyclerOptions<ChallengeDTO> options = new FirebaseRecyclerOptions.Builder<ChallengeDTO>()
                .setQuery(query, ChallengeDTO.class)
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        layoutManager.setReverseLayout(false);
        adapter = new ChallengeMainListActivity.ChallengeListAdapter(options);
        recyclerView.setAdapter(adapter);

        ImageButton waitListButton = findViewById(R.id.waiting_list_button);
        waitListButton.setOnClickListener(view-> {
            Intent intent = new Intent(this, ChallengeWaitingListActivity.class);
            startActivity(intent);
        });

        ImageButton createButton = findViewById(R.id.create_activity_button);
        createButton.setOnClickListener(view-> {
            Intent intent = new Intent(this, ChallengeCreateActivity.class);
            startActivity(intent);
        });
    }

    private static class ChallengeListViewHolder extends RecyclerView.ViewHolder {
        final public TextView nameView;
        final public TextView contentView;
        final public ImageButton imageButton;
        final public CardView cardView;

        public ChallengeListViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.challenge_item_name);
            contentView = itemView.findViewById(R.id.challenge_item_content);
            imageButton = itemView.findViewById(R.id.challenge_image_button);
            cardView = itemView.findViewById(R.id.challenge_list_card);
        }
    }

    private class ChallengeListAdapter extends FirebaseRecyclerAdapter<ChallengeDTO, ChallengeMainListActivity.ChallengeListViewHolder> {
        public ChallengeListAdapter(@NonNull FirebaseRecyclerOptions<ChallengeDTO> options) {
            super(options);
        }

        @NonNull
        @Override
        public ChallengeMainListActivity.ChallengeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) ChallengeMainListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.challenge_item, parent, false);
            return new ChallengeMainListActivity.ChallengeListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChallengeMainListActivity.ChallengeListViewHolder holder, int position, @NonNull ChallengeDTO challengeDTO) {
            holder.nameView.setText(getString(R.string.sir, challengeDTO.getName()));
            holder.contentView.setText(challengeDTO.getContent());
            holder.imageButton.setImageResource(R.drawable.ic_polar_bear);
            holder.imageButton.setOnClickListener(view -> {
                Intent intent = new Intent(ChallengeMainListActivity.this, ChallengeActionActivity.class);
                intent.putExtra("content", challengeDTO.getContent());
                startActivity(intent);
            });
            holder.cardView.setOnClickListener(view -> {
                Intent intent = new Intent(ChallengeMainListActivity.this, ChallengeDetailActivity.class);
                intent.putExtra("content", challengeDTO.getContent());
                startActivity(intent);
            });
        }
    }
}
