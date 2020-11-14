package com.pla_bear.board.review;

import android.os.Bundle;

public class TextReviewDetailFragment extends ReviewDetailFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path += SEPARATOR + "text";
    }
}
