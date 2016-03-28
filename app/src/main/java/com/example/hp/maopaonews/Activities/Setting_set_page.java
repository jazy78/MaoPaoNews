package com.example.hp.maopaonews.Activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.content_fragment.SettingsFragment;
import com.umeng.socialize.utils.Log;

/**
 * Created by hp on 2016/1/14.
 */
    public class Setting_set_page extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hgz_set_page_layout);
        InstanceActivtiyApplication();
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Log.d("III","Setting_set_page");


        FragmentManager fragmentManager=getFragmentManager();
        android.app.FragmentTransaction transaction=fragmentManager.beginTransaction();
        PreferenceFragment fragment=new SettingsFragment();
        transaction.replace(R.id.fragment_contain,fragment).commit();



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                Log.d("III","我惦记了");
                break;

        }
        return  true;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case android.R.id.home:
                finish();

        }
    }


    private void InstanceActivtiyApplication(){

        ActivityManager activityManager= ActivityManager.getInstance();
        activityManager.addActivity(this);
    }
}
