package com.pla_bear.board.challenge;

import com.pla_bear.board.base.BoardDTO;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class ChallengeDTO extends BoardDTO {
    private ArrayList<String> likes;

    public ChallengeDTO() {}

    public ChallengeDTO(String uid, String name, String content, ArrayList<String> likes) {
        super(uid, name, content);
        this.likes = likes;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }
}
