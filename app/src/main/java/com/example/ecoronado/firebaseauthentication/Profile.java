package com.example.ecoronado.firebaseauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Profile extends AppCompatActivity {

    EditText input_name;
    ImageButton btnImage;
    Button btnSubmit;
    RelativeLayout activity_profile;

    private Uri imageUri = null;

    private static final int GALLERY_REQUEST = 1;

    private ProgressDialog progressBar;

    private DatabaseReference databaseUser;
    private FirebaseAuth auth;
    private StorageReference storageImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressBar = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
        storageImage = FirebaseStorage.getInstance().getReference().child("profile_images");
        databaseUser = FirebaseDatabase.getInstance().getReference().child("Users");

        input_name = (EditText)findViewById(R.id.login_name);
        btnImage = (ImageButton)findViewById(R.id.imageButton);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        activity_profile = (RelativeLayout)findViewById(R.id.activity_profile);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupAccount();
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

    private void setupAccount() {
        final String userName = input_name.getText().toString().trim();
        final String userId = auth.getCurrentUser().getUid();

        if(!userName.isEmpty() && imageUri != null){
            progressBar.setMessage("Finishing...");
            progressBar.show();

            StorageReference filePath = storageImage.child(imageUri.getLastPathSegment());
            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String downloadUri = taskSnapshot.getDownloadUrl().toString();

                    databaseUser.child(userId).child("username").setValue(userName);
                    databaseUser.child(userId).child("image").setValue(downloadUri);

                    Intent intent = new Intent(Profile.this, Dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

        }
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
