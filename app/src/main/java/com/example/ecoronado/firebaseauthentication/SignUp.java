package com.example.ecoronado.firebaseauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class SignUp extends AppCompatActivity {

    Button btnSignUp;
    ImageButton btnImage;
    EditText input_email, input_pass, input_confirmPass, input_name;
    RelativeLayout activity_sign_up;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private StorageReference storageImage;

    private ProgressDialog mProgress;

    private Uri imageUri = null;

    private static final int GALLERY_REQUEST = 1;

    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button)findViewById(R.id.signup_btn_register);
        btnImage = (ImageButton)findViewById(R.id.imageUser);
        input_email = (EditText)findViewById(R.id.signup_email);
        input_pass = (EditText)findViewById(R.id.sign_pass);
        input_confirmPass = (EditText)findViewById(R.id.confirm_pass);
        input_name = (EditText)findViewById(R.id.login_name);
        activity_sign_up = (RelativeLayout)findViewById(R.id.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();
        storageImage = FirebaseStorage.getInstance().getReference().child("profile_images");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mProgress = new ProgressDialog(this);


                btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String userId = mAuth.getCurrentUser().getUid();
                signUpUser(input_email.getText().toString(), input_pass.getText().toString());
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });
    }

    private void signUpUser(String email, String pass) {
        final String name = input_name.getText().toString().trim();


        mProgress.setMessage("Signing up...");
        mProgress.show();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(!task.isSuccessful()){
                           snackbar = Snackbar.make(activity_sign_up, "Error: "+task.getException(), Snackbar.LENGTH_SHORT);
                           snackbar.show();
                       }
                        else{
                           final String userId = mAuth.getCurrentUser().getUid();
                           mDatabaseUsers.child(userId).child("username").setValue(name);

                           StorageReference filePath = storageImage.child(imageUri.getLastPathSegment());
                           filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                               @Override
                               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                   String downloadUri = taskSnapshot.getDownloadUrl().toString();

                                   //mDatabaseUsers.child(userId).child("username").setValue(name);
                                   mDatabaseUsers.child(userId).child("image").setValue(downloadUri);

                                   Intent intent = new Intent(SignUp.this, Dashboard.class);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                   startActivity(intent);
                               }
                           });

                           startActivity(new Intent(SignUp.this, MainActivity.class));
                       }

                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                imageUri = result.getUri();
                btnImage.setImageURI(imageUri);
            }
        }
    }
}
