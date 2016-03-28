package com.example.hp.maopaonews.CostomProgressDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.maopaonews.R;

/**
 * Created by hp on 2016/1/6.
 */
public class CustomProgressDialog extends ProgressDialog {


    private Context mContext;
    private String mLoadingTip;
    private int mResid;
    private TextView mLoadingTv;
    private AnimationDrawable mAnimation;
    private  ImageView mImageView;


    public CustomProgressDialog(Context context,String content,int id) {
        super(context);
        this.mContext=context;
        mLoadingTip=content;
        mResid=id;
        setCanceledOnTouchOutside(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData(){
        mImageView.setBackgroundResource(mResid);
        mAnimation=(AnimationDrawable)mImageView.getBackground();
       /* Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.animation_frame);
        animation.start();*/
        mImageView.post(new Runnable() {
            @Override
            public void run() {
            mAnimation.start();
            }
        });
          mLoadingTv.setText(mLoadingTip);

    }
    public void setContent(String str) {
        mLoadingTv.setText(str);}

    private void initView(){
        setContentView(R.layout.progress_dialog);
        mLoadingTv=(TextView)findViewById(R.id.loadingTv);
        mImageView=(ImageView)findViewById(R.id.loadingIv);
    }
}
