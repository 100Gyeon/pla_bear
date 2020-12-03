package com.pla_bear.board.base;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class BoardDTO {
    public String uid;
    public String name;
    public String content;

    public BoardDTO(){}

    public BoardDTO(String uid, String name, String content){
        this.uid=uid;
        this.name=name;
        this.content=content;
    }
    public String getUid(){
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
