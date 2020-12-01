package com.pla_bear.base;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PointManager {
    static private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    static private final DatabaseReference databaseReference = database.getReference();
    static private final DatabaseReference pointReference = databaseReference.child("point");
    static private final HashMap<String, PointDTO> userMap = new HashMap<>();
    static private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    static public final int POINT_CHALLENGE = 2;
    static public final int POINT_QRCODE = 3;


    public static void load (){
        pointReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uid = firebaseUser.getUid();
                DataSnapshot child = snapshot.child(uid);
                PointDTO pointDTO = child.getValue(PointDTO.class);
                userMap.put(uid, pointDTO);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static void addPoint(int point) {
        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();
        long newPoint;

        if(userMap.containsKey(uid)) {
            PointDTO prev = userMap.get(uid);
            if(prev != null) {
                newPoint = prev.getPoint() + point;
            } else {
                newPoint = point;
            }

            PointDTO pointDTO = new PointDTO(newPoint, name, uid);
            pointReference.child(uid).setValue(pointDTO);
        }
    }
}
