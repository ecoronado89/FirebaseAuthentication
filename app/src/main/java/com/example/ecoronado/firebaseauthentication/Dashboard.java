package com.example.ecoronado.firebaseauthentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {

    TextView btnSignOut;
    EditText username;
    private FirebaseAuth user;
    private DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnSignOut = (TextView)findViewById(R.id.sign_out);
        username = (EditText)findViewById(R.id.user);
        user = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference().child("Users");

        if(user.getCurrentUser() != null){
            databaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                   String name = dataSnapshot.child("username").getValue(String.class);
                    username.setText(name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
           // username.setText("Welcome, "+user.getCurrentUser().getDisplayName());


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.signOut();
                if(user.getCurrentUser() == null){
                    Intent intent = new Intent(Dashboard.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}
