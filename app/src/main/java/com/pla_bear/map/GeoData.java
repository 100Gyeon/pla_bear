package com.pla_bear.map;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GeoData {
    public static ArrayList<Data> info;

    public static void loadData(){
        FirebaseFirestore firestore;
        firestore = FirebaseFirestore.getInstance();
        info = new ArrayList<>();

        firestore.document("geodata/place").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();

                        HashMap list = (HashMap) doc.getData().get("list");
                        Iterator<String> itr = list.keySet().iterator();
                        while(itr.hasNext()){
                            String key = itr.next();
                            HashMap<String, Object> map = (HashMap<String, Object>) list.get(key);
                            Data data = new Data();
                            data.placeName = map.get("name").toString();
                            data.placeSnip = map.get("snip").toString();
                            data.placeTel = map.get("tel").toString();
                            data.placeLat = (Double)map.get("lat");
                            data.placeLng = (Double)map.get("lng");
                            data.placeWeb = map.get("web").toString();
                            info.add(data);
                        }
                    }
                });
    }

    public static ArrayList<Data> getAddressData(){
        return info;
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
