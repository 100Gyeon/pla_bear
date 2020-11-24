package com.pla_bear.board.challenge;

import com.pla_bear.board.base.BoardDTO;

@SuppressWarnings("unused")
public class ChallengeBoardDTO extends BoardDTO {
    private String imageUrl;

    public ChallengeBoardDTO() {}

    public ChallengeBoardDTO(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ChallengeBoardDTO(String uid, String name, String content, String imageUrl) {
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
