package com.pla_bear.map;

import java.util.List;

public class GeoDTOContainer {
    private List<GeoDTO> info;

    public GeoDTOContainer() {}

    public List<GeoDTO> getInfo() {
        return info;
    }

    public void setInfo(List<GeoDTO> info) {
        this.info = info;
    }

    public GeoDTOContainer(List<GeoDTO> info) {
        this.info = info;
    }
}
