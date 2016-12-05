package com.example.ecoronado.firebaseauthentication;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by llbean on 12/4/16.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
