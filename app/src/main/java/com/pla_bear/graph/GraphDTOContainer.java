package com.pla_bear.graph;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GraphDTOContainer {
    @SerializedName("data")
    private List<GraphDTO> data;

    public List<GraphDTO> getData() {
        return data;
    }

    public void setData(List<GraphDTO> data) {
        this.data = data;
    }

    public GraphDTOContainer(List<GraphDTO> data) {
        this.data = data;
    }
}
