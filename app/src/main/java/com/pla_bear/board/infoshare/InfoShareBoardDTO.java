package com.pla_bear.board.infoshare;

import com.pla_bear.board.base.BoardDTO;

import java.util.List;

public class InfoShareBoardDTO extends BoardDTO {
    public String imageUrl;

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
