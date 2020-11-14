package com.pla_bear.board.review;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

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
import com.google.firebase.database.Query;
import com.pla_bear.R;
import com.pla_bear.board.base.DetailFragment;

import java.util.List;

public class ReviewDetailFragment extends DetailFragment {
    protected String path;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            path = getArguments().getString("path");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_detail, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.review_recycler_view);

        Query query = databaseReference.child(path);
        FirebaseRecyclerOptions<ReviewBoardDTO> options = new FirebaseRecyclerOptions.Builder<ReviewBoardDTO>()
                .setQuery(query, ReviewBoardDTO.class)
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        layoutManager.setReverseLayout(false);
        adapter = new ReviewDetailFragment.ReviewDetailAdapter(options);
        recyclerView.setAdapter(adapter);
        return view;
    }

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

    private class ReviewDetailAdapter extends FirebaseRecyclerAdapter<ReviewBoardDTO, ReviewDetailFragment.ReviewDetailViewHolder> {
        public ReviewDetailAdapter(@NonNull FirebaseRecyclerOptions<ReviewBoardDTO> options) {
            super(options);
        }

        @NonNull
        @Override
        public ReviewDetailFragment.ReviewDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.review_detail_item, parent, false);
            return new ReviewDetailFragment.ReviewDetailViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewDetailFragment.ReviewDetailViewHolder holder, int position, @NonNull ReviewBoardDTO reviewBoardDTO) {
            holder.nameView.setText(reviewBoardDTO.getName() + " 님");
            holder.contentView.setText(reviewBoardDTO.getContent());
            holder.ratingBar.setRating(reviewBoardDTO.getRating());

            List<String> imageUrl = reviewBoardDTO.getImageUrl();
            if(imageUrl != null) {
                for (int i = 0; i < imageUrl.size(); i++) {
                    ImageView imageView = (ImageView) holder.imageWrap.getChildAt(i);
                    Glide.with(mContext)
                            .load(imageUrl.get(i))
                            .into(imageView);
                }
            }
        }
    }

}
