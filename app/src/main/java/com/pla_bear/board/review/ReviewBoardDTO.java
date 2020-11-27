package com.pla_bear.board.review;

import com.pla_bear.board.base.BoardDTO;

import java.util.List;

@SuppressWarnings("unused")
public class ReviewBoardDTO extends BoardDTO {
    private float rating;
    private List<String> imageUrl;

    public ReviewBoardDTO() {
    }

    public ReviewBoardDTO(String uid, String name, String content, float rating, List<String> imageUrl) {
        super(uid, name, content);
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) { this.imageUrl = imageUrl; }
    public List<String> getImageUrl() {return this.imageUrl; }
    public float getRating() {
        return this.rating;
    }

    public void setRating(int rate) {
        this.rating = rate;
    }
}
