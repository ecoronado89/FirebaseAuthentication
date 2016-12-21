package com.example.ecoronado.firebaseauthentication;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by crrf on 12/19/2016.
 */

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved = null;
    ArrayList<Message> infos = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public Boolean save(Message info) {
        if(info == null) {
            saved = false;
        } else {
            try {
                db.child("colores").push().setValue(info);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        infos.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Message message = ds.getValue(Message.class);
            infos.add(message);
        }
    }

    public ArrayList<Message> retrieve() {
        /*db.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });*/

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    Info info = locationSnapshot.getValue(Info.class);
                    infos.add(info);
                }*/

                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return infos;
    }
}
