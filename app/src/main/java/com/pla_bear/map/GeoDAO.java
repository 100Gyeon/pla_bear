package com.pla_bear.map;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GeoDAO {
    private static List<GeoDTO> addressData = new ArrayList<>();

    public static void loadData(){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.document("geodata/place").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        GeoListDTO geoListDTO = Objects.requireNonNull(doc.toObject(GeoListDTO.class));
                        addressData = geoListDTO.getInfo();
                    }
                });
    }

    public static List<GeoDTO> getAddressData(){
        return addressData;
    }
}