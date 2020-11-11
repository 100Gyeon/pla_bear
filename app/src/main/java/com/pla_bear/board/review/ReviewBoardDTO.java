package com.pla_bear.board.review;

import android.net.Uri;

import com.pla_bear.board.base.BoardDTO;

public class ReviewBoardDTO extends BoardDTO {
    private float rating;
    private Uri[] imageUrl;

    public ReviewBoardDTO() {
    }

    public ReviewBoardDTO(String uid, String name, String content, float rating, Uri[] imageUrl) {
        super(uid, name, content);
        this.rating = rating;
        //this.imageUrl = imageUrl;
    }

    public float getRating() {
        return this.rating;
    }

    public void setRating(int rate) {
        this.rating = rate;
    }
}
