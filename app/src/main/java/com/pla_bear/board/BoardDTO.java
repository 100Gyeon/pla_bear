package com.pla_bear.board;

public class BoardDTO {
    public String imageUrl;
    public String name;
    public String content;

    public BoardDTO(){}

    public BoardDTO(String imageUrl,String name, String content){
        this.imageUrl=imageUrl;
        this.name=name;
        this.content=content;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public String getName(){
        return name;
    }
    public String getContent(){
        return content;
    }
}
