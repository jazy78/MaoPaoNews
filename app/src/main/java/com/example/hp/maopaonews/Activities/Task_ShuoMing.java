package com.example.hp.maopaonews.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.hp.maopaonews.R;

/**
 * Created by hp on 2016/1/18.
 */
public class Task_ShuoMing extends AppCompatActivity {

    private ImageView backsetting;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__shuo_ming);

        initView();
    }

    private void initView(){
        backsetting= (ImageView) findViewById(R.id.backsetting);
        backsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
