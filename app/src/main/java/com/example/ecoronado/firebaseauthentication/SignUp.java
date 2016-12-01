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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button btnSignUp;
    EditText input_email, input_pass, input_confirmPass, input_name;
    RelativeLayout activity_sign_up;

    private FirebaseAuth auth;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button)findViewById(R.id.login_btn_signup);
        input_email = (EditText)findViewById(R.id.signup_input_email);
        input_pass = (EditText)findViewById(R.id.signup_input_password);
        input_confirmPass = (EditText)findViewById(R.id.signup_input_confirm_password);
        input_name = (EditText)findViewById(R.id.signup_input_name);
        activity_sign_up = (RelativeLayout)findViewById(R.id.activity_sign_up);

        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        signUpUser(input_email.getText().toString(), input_pass.getText().toString(), input_confirmPass.getText().toString(), input_name.getText().toString());
    }

    private void signUpUser(String email, String pass, String confirm_pass, String name) {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(!task.isSuccessful()){
                           snackbar = Snackbar.make(activity_sign_up, "Error: "+task.getException(), Snackbar.LENGTH_SHORT);
                           snackbar.show();
                       }
                        else{
                           snackbar = Snackbar.make(activity_sign_up, "Registration was success", Snackbar.LENGTH_SHORT);
                           snackbar.show();
                       }
                    }
                });
    }
}
