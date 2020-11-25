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
import java.util.Map;

public class PointManager {
    static private FirebaseDatabase database = FirebaseDatabase.getInstance();
    static private DatabaseReference databaseReference = database.getReference();
    static private DatabaseReference pointReference = databaseReference.child("point");
    static private HashMap<String, Long> userMap = new HashMap<>();
    static private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public static void load (){
        pointReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uid = firebaseUser.getUid();
                DataSnapshot child = snapshot.child(uid);

                Long point = child.getValue(Long.class);
                userMap.put(uid, point);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static void addPoint(int point) {
        String uid = firebaseUser.getUid();

        if(userMap.containsKey(uid)) {
            Long prev = userMap.get(uid);
            if(prev != null) {
                pointReference.child(uid).setValue(prev + point);
            }
        } else {
            pointReference.child(uid).setValue(point);
        }
    }
}
