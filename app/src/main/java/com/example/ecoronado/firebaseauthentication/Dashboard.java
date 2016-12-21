package com.example.ecoronado.firebaseauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ecoronado.firebaseauthentication.usuarios.Constants;
import com.example.ecoronado.firebaseauthentication.usuarios.Info;
import com.example.ecoronado.firebaseauthentication.usuarios.InfoAdapter;
import com.example.ecoronado.firebaseauthentication.usuarios.InfoChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {


    private FirebaseAuth user;
    private DatabaseReference databaseUser;
    private DatabaseReference myRef;
    private FirebaseUser mUser;
    private String sender = "VACIO";
    private ArrayList<Info> infos = new ArrayList<>();
    private Info info;
    private InfoChat infoChat;
    private InfoAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        user = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        infoChat = new InfoChat();

        databaseUser = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());


        if(user.getCurrentUser() != null){
            databaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    sender = dataSnapshot.child("username").getValue(String.class);

                    Log.i("info","+++++++++++++++USERNAME");
                    System.out.println("SENDER = " + sender);
                    Log.i("info","+++++++++++++++USERNAME");

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }

        myRef = FirebaseDatabase.getInstance().getReference("Users");

        adapter = new InfoAdapter(this, infos, new InfoAdapter.OnItemClickListener(){
            @Override public void onItemClick(Info info) {
                intent = new Intent(adapter.getContext(), Chat.class);

                Log.i("info","+++++++++++++++ONCLICK");
                    System.out.println("IMAGE = " + info.getImage());
                    System.out.println("USERNAME = " + info.getUsername());
                Log.i("info","+++++++++++++++ONCLICK");

                infoChat.setSender(sender);
                infoChat.setReceiver(info.getUsername());

                intent.putExtra(Constants.INFO_CHAT, infoChat);
                startActivity(intent);
            }
        });

        infos.clear();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    info = locationSnapshot.getValue(Info.class);
                    if (!info.getUsername().equals(sender)){
                        infos.add(info);
                    }

                    Log.i("info","+++++++++++++++LISTENER");
                    System.out.println("IMAGE = " + info.getImage());
                    System.out.println("USERNAME = " + info.getUsername());
                    Log.i("info","+++++++++++++++LISTENER");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        recyclerView = (RecyclerView) findViewById(R.id.info_list);
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }
}
