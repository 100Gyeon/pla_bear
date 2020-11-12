package com.pla_bear.board.infoshare;

import com.pla_bear.board.base.BoardDTO;

import java.util.List;

public class InfoShareBoardDTO extends BoardDTO {
    private List<String> imageUrl;

    public InfoShareBoardDTO() {
    }

    public InfoShareBoardDTO(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public InfoShareBoardDTO(String uid, String name, String content, List<String> imageUrl) {
        super(uid, name, content);
        this.imageUrl = imageUrl;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
