package com.pla_bear.map;

import java.util.List;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class GeoListDTO {
    private List<GeoDTO> info;

    public GeoListDTO() {}

    public List<GeoDTO> getInfo() {
        return info;
    }

    public void setInfo(List<GeoDTO> info) {
        this.info = info;
    }

    public GeoListDTO(List<GeoDTO> info) {
        this.info = info;
    }
}
