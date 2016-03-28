package com.example.hp.maopaonews.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.content_fragment.Private_fragment;

/**
 * Created by hp on 2016/1/15.
 */
public class PrivateActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_activity);
        InstanceActivtiyApplication();
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("个人信息设置");

        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();

        Private_fragment fragment=new Private_fragment();

        transaction.replace(R.id.contain,fragment).commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void InstanceActivtiyApplication(){

        ActivityManager activityManager= ActivityManager.getInstance();
        activityManager.addActivity(this);
    }
}
