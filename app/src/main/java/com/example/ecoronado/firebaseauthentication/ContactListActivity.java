package com.example.ecoronado.firebaseauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ContactListActivity extends BaseActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        Button button = (Button) findViewById(R.id.button_holder);
        button.setOnClickListener(this);

        // TODO Put the home icon
         //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.contact_list_layout;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_holder){
            startActivity(new Intent(ContactListActivity.this,ChatListActivity.class));
            //finish();
        }
    }

}
