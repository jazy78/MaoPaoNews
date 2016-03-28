package com.example.hp.maopaonews.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.welcome.ScrollViewListener;
import com.example.hp.maopaonews.welcome.WelcomeScollView;

/**
 * Created by hp on 2016/1/6.
 */
public class WelcomeActivity extends AppCompatActivity implements ScrollViewListener{

    private LinearLayout mLLAnim;
    private WelcomeScollView mSVmain;
    private TextView tvInNew;

    private int mScrollViewHeight;
    private int mStartAnimateTop;
    private boolean hasStart = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        InstanceActivtiyApplication();
        boolean isFirst=getSharedPreferences("welcome",MODE_PRIVATE)
                .getBoolean("isFirst",true);
        initView();
        if(isFirst){
            setView();

        }else {

            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

    }
    private void InstanceActivtiyApplication(){

        ActivityManager activityManager= ActivityManager.getInstance();
        activityManager.addActivity(this);
    }
    public  void setView(){
        mLLAnim.setVisibility(View.INVISIBLE);
        mSVmain.setScrollViewListener(this);
        tvInNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                getSharedPreferences("welcome",MODE_PRIVATE).edit().putBoolean("isFirst",false).commit();
                finish();
            }
        });

    }

    private void initView(){
        mLLAnim= (LinearLayout) findViewById(R.id.ll_anim);
        mSVmain = (WelcomeScollView) findViewById(R.id.sv_main);
        tvInNew= (TextView) findViewById(R.id.tvInNew);

    }

    @Override
    public void scrollViewListener(int top, int oldTop) {
        int animTop=mLLAnim.getTop()-top;
        Log.d("AAA","oldTop="+oldTop);
        Log.d("AAA","top="+top);
        Log.d("AAA","animTop="+animTop);
        if(top>oldTop){
            if(animTop<mStartAnimateTop&&!hasStart){
                Animation animation= AnimationUtils.loadAnimation(this,R.anim.show);
                mLLAnim.setVisibility(View.VISIBLE);
                mLLAnim.startAnimation(animation);
                hasStart=true;
          }


        }else {
                if(animTop>mStartAnimateTop&&hasStart){
                    Animation animation=AnimationUtils.loadAnimation(this,R.anim.colse);
                    mLLAnim.setVisibility(View.INVISIBLE);
                    mLLAnim.startAnimation(animation);
                    hasStart=false;
                }

        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mScrollViewHeight = mSVmain.getHeight();//1701
        mStartAnimateTop = mScrollViewHeight / 5 * 4;//1360
        Log.d("AAA","mScrollViewHeight="+mScrollViewHeight);
        Log.d("AAA"," mStartAnimateTop="+ mStartAnimateTop);
    }
}
