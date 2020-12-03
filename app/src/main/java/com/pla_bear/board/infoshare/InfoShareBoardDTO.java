package com.pla_bear.board.infoshare;

import com.pla_bear.board.base.BoardDTO;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class InfoShareBoardDTO extends BoardDTO {
    private String imageUrl;

    public InfoShareBoardDTO() {
    }

    public InfoShareBoardDTO(String uid, String name, String content, String imageUrl) {
        super(uid, name, content);
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
