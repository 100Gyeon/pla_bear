package com.pla_bear.map;

@SuppressWarnings("unused")
public class GeoDTO {
    private String placeName;
    private String placeSnip;
    private String placeTel;
    private double placeLat;
    private double placeLng;
    private String placeWeb;

    public GeoDTO() {

    }

    public GeoDTO(String placeName, String placeSnip, String placeTel, double placeLat, double placeLng, String placeWeb) {
        this.placeName = placeName;
        this.placeSnip = placeSnip;
        this.placeTel = placeTel;
        this.placeLat = placeLat;
        this.placeLng = placeLng;
        this.placeWeb = placeWeb;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceSnip() {
        return placeSnip;
    }

    public void setPlaceSnip(String placeSnip) {
        this.placeSnip = placeSnip;
    }

    public String getPlaceTel() {
        return placeTel;
    }

    public void setPlaceTel(String placeTel) {
        this.placeTel = placeTel;
    }

    public double getPlaceLat() {
        return placeLat;
    }

    public void setPlaceLat(double placeLat) {
        this.placeLat = placeLat;
    }

    public double getPlaceLng() {
        return placeLng;
    }

    public void setPlaceLng(double placeLng) {
        this.placeLng = placeLng;
    }

    public String getPlaceWeb() {
        return placeWeb;
    }

    public void setPlaceWeb(String placeWeb) {
        this.placeWeb = placeWeb;
    }
}
