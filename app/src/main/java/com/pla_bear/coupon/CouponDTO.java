package com.pla_bear.coupon;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("unused")
public class CouponDTO implements Serializable {
    private static final long serialVersionUID = 2;

    @SerializedName("name")
    private String name;
    @SerializedName("edate")
    private Date edate;
    @SerializedName("uid")
    private String uid;
    @SerializedName("price")
    private int price;
    @SerializedName("cdate")
    private Date cdate;
    @SerializedName("barcode")
    private String barcode;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public Date getEdate() { return this.edate; }

    public void setEdate(Date edate) { this.edate = edate; }

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

    public String getBarcode() {
        return this.barcode;
    }
}
