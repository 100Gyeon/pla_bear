package com.pla_bear.graph;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GraphListDTO {
    @SerializedName("data")
    private List<GraphDTO> data;

    public List<GraphDTO> getData() {
        return data;
    }

    public void setData(List<GraphDTO> data) {
        this.data = data;
    }

    public GraphListDTO(List<GraphDTO> data) {
        this.data = data;
    }
}
