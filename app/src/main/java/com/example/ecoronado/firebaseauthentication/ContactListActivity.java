package com.example.ecoronado.firebaseauthentication;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class ContactListActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());


        // TODO Put the home icon
         //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.contact_list_layout;
    }

}
