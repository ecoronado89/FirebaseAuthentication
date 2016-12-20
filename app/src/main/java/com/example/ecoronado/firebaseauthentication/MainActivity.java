package com.example.ecoronado.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText input_email, input_password;
    TextView btnSignUp, btnForgotPass;

    RelativeLayout activity_main;

    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.login_btn_login);
        input_email = (EditText)findViewById(R.id.login_email);
        input_password = (EditText)findViewById(R.id.login_pass);
        btnSignUp = (TextView)findViewById(R.id.login_btn_signup);
        btnForgotPass = (TextView)findViewById(R.id.forgot_password);
        activity_main = (RelativeLayout)findViewById(R.id.activity_main);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, Profile.class));
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.forgot_password){
            startActivity(new Intent(MainActivity.this,ForgotPass.class));
            finish();
        }
        else if(view.getId() == R.id.login_btn_signup){
            startActivity(new Intent(MainActivity.this,SignUp.class));
            finish();
        }
        else if(view.getId() == R.id.login_btn_login){
            loginUser(input_email.getText().toString(), input_password.getText().toString());
        }
    }

    private void loginUser(String email, final String password) {
        if(email.isEmpty() || password.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.login_error_message)
                    .setTitle(R.string.login_error_title)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog  = builder.create();
            dialog.show();
        }else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                if (password.length() < 6) {
                                    /*Snackbar snackBar = Snackbar.make(activity_main, "Password length must be over 6", Snackbar.LENGTH_SHORT);
                                    snackBar.show();*/
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage(task.getException().getMessage())
                                            .setTitle(R.string.login_error_title)
                                            .setPositiveButton(android.R.string.ok, null);
                                    AlertDialog dialog  = builder.create();
                                    dialog.show();
                                }
                            } else {
                                Intent intent = new Intent(MainActivity.this, Profile.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }
                    });
        }
    }
}
