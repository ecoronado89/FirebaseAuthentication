package com.example.ecoronado.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail;
    private Button btnResetPass;
    private TextView btnBack;
    private RelativeLayout activity_forgot;

    private FirebaseAuth forgotUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        inputEmail = (EditText)findViewById(R.id.forgot_email);
        btnBack = (TextView)findViewById(R.id.btnBack);
        btnResetPass = (Button)findViewById(R.id.forgot_btn_email);

        activity_forgot = (RelativeLayout)findViewById(R.id.activity_forgot_pass);

        forgotUser = FirebaseAuth.getInstance();

        btnResetPass.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.btnBack){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else if(view.getId() == R.id.forgot_btn_email){
            resetPassword(inputEmail.getText().toString());
        }
    }

    private void resetPassword(final String email) {
        forgotUser.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Snackbar snackBar = Snackbar.make(activity_forgot,"We have sent a password to "+email, Snackbar.LENGTH_SHORT);
                            snackBar.show();
                        }else{
                            Snackbar snackBar = Snackbar.make(activity_forgot,"Failed to send password", Snackbar.LENGTH_SHORT);
                            snackBar.show();
                        }
                    }
                });
    }


}
