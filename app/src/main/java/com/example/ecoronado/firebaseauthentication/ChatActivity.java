package com.example.ecoronado.firebaseauthentication;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

/**
 * Created by llbean on 12/4/16.
 */

public class ChatActivity extends BaseActivity{

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setHomeButtonEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.chat_activity;
    }
}
