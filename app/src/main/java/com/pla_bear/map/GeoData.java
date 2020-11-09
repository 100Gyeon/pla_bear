package com.pla_bear.map;

import java.util.ArrayList;

public class GeoData {
    public static ArrayList<Data> getAddressData(){
        ArrayList<Data> list = new ArrayList<>();
        Data data = new Data();
        data.placeName = "통인시장";
        data.placeSnip = "대부분의 과일, 채소, 곡물을 포장재 없이 진열하는 시장입니다.";
        data.placeTel = "tel:027220911";
        data.placeLat = 37.580728;
        data.placeLng = 126.969986;
        data.placeWeb = "https://tonginmarket.modoo.at/";
        list.add(data);

        return list;
    }
}

class Data {
    String placeName;
    String placeSnip;
    String placeTel;
    double placeLat;
    double placeLng;
    String placeWeb;
}
