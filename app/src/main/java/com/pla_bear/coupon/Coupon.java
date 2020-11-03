package com.pla_bear.coupon;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Coupon {
    @SerializedName("edate")
    private Date edate;
    @SerializedName("uid")
    private String uid;
    @SerializedName("price")
    private int price;
    @SerializedName("cdate")
    private Date cdate;
    @SerializedName("barcode")
    private int barcode;

    public Date getEdate() {
        return this.edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCdate() {
        return this.cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public int getBarcode() {
        return this.barcode;
    }
}
