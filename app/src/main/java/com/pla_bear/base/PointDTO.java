package com.pla_bear.base;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class PointDTO {
    private long point;
    private String name;
    private String uid;

    public PointDTO() {}
    public PointDTO(long point, String name, String uid) {
        this.point = point;
        this.name = name;
        this.uid = uid;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
