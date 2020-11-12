package com.pla_bear.board.review;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.pla_bear.R;
import com.pla_bear.board.base.DetailActivity;

import java.util.List;

public class ReviewDetailActivity extends DetailActivity {
    private static class ReviewDetailViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public RatingBar ratingBar;
        public TextView contentView;
        public LinearLayout imageWrap;

        public ReviewDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.review_item_name);
            ratingBar = itemView.findViewById(R.id.review_item_ratingbar);
            contentView = itemView.findViewById(R.id.review_item_content);
            imageWrap = itemView.findViewById(R.id.review_item_image_wrap);
        }
    }

    private class ReviewDetailAdapter extends FirebaseRecyclerAdapter<ReviewBoardDTO, ReviewDetailViewHolder> {
        public ReviewDetailAdapter(@NonNull FirebaseRecyclerOptions<ReviewBoardDTO> options) {
            super(options);
        }

        @NonNull
        @Override
        public ReviewDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)ReviewDetailActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.review_detail_item, parent, false);
            return new ReviewDetailViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewDetailViewHolder holder, int position, @NonNull ReviewBoardDTO reviewBoardDTO) {
            holder.nameView.setText(reviewBoardDTO.getName() + " 님");
            holder.contentView.setText(reviewBoardDTO.getContent());
            holder.ratingBar.setRating(reviewBoardDTO.getRating());

            List<String> imageUrl = reviewBoardDTO.getImageUrl();
            if(imageUrl != null) {
                for (int i = 0; i < imageUrl.size(); i++) {
                    ImageView imageView = (ImageView) holder.imageWrap.getChildAt(i);
                    Glide.with(ReviewDetailActivity.this)
                            .load(imageUrl.get(i))
                            .into(imageView);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail_acitivity);

        RecyclerView recyclerView = findViewById(R.id.review_recycler_view);
        Query query = databaseReference.child(getString(R.string.review_database));
        FirebaseRecyclerOptions<ReviewBoardDTO> options = new FirebaseRecyclerOptions.Builder<ReviewBoardDTO>()
                .setQuery(query, ReviewBoardDTO.class)
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        layoutManager.setReverseLayout(false);
        adapter = new ReviewDetailAdapter(options);
        recyclerView.setAdapter(adapter);

    }
}