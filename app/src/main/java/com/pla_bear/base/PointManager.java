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

    static public final FirebaseDatabase database = FirebaseDatabase.getInstance();
    static public final DatabaseReference databaseReference = database.getReference();
    static public final DatabaseReference pointReference = databaseReference.child("point2");
    static public final HashMap<String,Long> userMap = new HashMap<>();
    static public final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    static public final int POINT_CHALLENGE = 2;
    static public final int POINT_QRCODE = 3;


    public static void load (){
        pointReference.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uid = firebaseUser.getUid();
                //String name=firebaseUser.getDisplayName();
                DataSnapshot child = snapshot.child(uid);
                String name=snapshot.child(uid).getKey();
                Long point =snapshot.child(uid).getValue(Long.class);
                userMap.put(name,point);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static void addPoint(int point) {
        String uid = firebaseUser.getUid();
       String name=firebaseUser.getDisplayName();

        if(userMap.containsKey(uid)) {
            Long prev =userMap.get(uid);
            if(prev != null) { //전에 있으면
                pointReference.child(uid).child(name).setValue(prev+point);
            }
        } else { //전에 없으면
            pointReference.child(uid).child(name).setValue(point);
        }

    }



        }
