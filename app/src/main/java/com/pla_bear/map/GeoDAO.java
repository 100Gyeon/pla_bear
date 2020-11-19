package com.pla_bear.map;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class GeoDAO {
    private static List<GeoDTO> addressData = new ArrayList<>();

    public static void loadData(){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.document("geodata/place").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        addressData = doc.toObject(GeoListDTO.class).getInfo();
                    }
                });
    }

    public static List<GeoDTO> getAddressData(){
        return addressData;
    }
}