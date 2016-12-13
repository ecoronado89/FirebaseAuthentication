package com.example.ecoronado.firebaseauthentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    TextView btnSignOut;
    EditText username;
    private FirebaseAuth user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnSignOut = (TextView)findViewById(R.id.sign_out);
        username = (EditText)findViewById(R.id.user);
        user = FirebaseAuth.getInstance();

        if(user.getCurrentUser() != null)
            username.setText("Welcome, "+user.getCurrentUser().getEmail());

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
