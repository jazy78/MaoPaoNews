package com.example.hp.maopaonews.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.hp.maopaonews.R;

/**
 * Created by hp on 2016/1/18.
 */
public class Setting_my_Task extends AppCompatActivity implements View.OnClickListener {

    private ImageView backsetting;
    private ImageView duihuan;
    private RelativeLayout fabiao, share, xinshang, read_text, read_news, open_client;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hgz_activity_my__task);
        initView();

    }

    private void initView(){

        backsetting = (ImageView) findViewById(R.id.backsetting);
        duihuan = (ImageView) findViewById(R.id.duihuan);
        fabiao = (RelativeLayout) findViewById(R.id.fabiao);
        share = (RelativeLayout) findViewById(R.id.share);
        xinshang = (RelativeLayout) findViewById(R.id.xinshang);
        read_text = (RelativeLayout) findViewById(R.id.read_text);
        read_news = (RelativeLayout) findViewById(R.id.read_news);
        open_client = (RelativeLayout) findViewById(R.id.open_client);

        backsetting.setOnClickListener(this);
        fabiao.setOnClickListener(this);
        duihuan.setOnClickListener(this);
        share.setOnClickListener(this);
        xinshang.setOnClickListener(this);
        read_text.setOnClickListener(this);
        read_news.setOnClickListener(this);
        open_client.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.backsetting:
                finish();
                break;
            case R.id.duihuan:
                Intent intent=new Intent(this,Setting_glodmall.class);
                startActivity(intent);
                finish();
                break;
            case R.id.fabiao:
                Intent intent1=new Intent(this,Task_ShuoMing.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.share:
                Intent intent2=new Intent(this,Task_ShuoMing.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.xinshang:

                Intent intent3=new Intent(this,Task_ShuoMing.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.read_text:
                Intent intent4=new Intent(this,Task_ShuoMing.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.read_news:
                Intent intent5=new Intent(this,Task_ShuoMing.class);
                startActivity(intent5);
                finish();
                break;
            case R.id.open_client:
                Intent intent6=new Intent(this,Task_ShuoMing.class);
                startActivity(intent6);
                finish();
                break;

        }
    }
}
