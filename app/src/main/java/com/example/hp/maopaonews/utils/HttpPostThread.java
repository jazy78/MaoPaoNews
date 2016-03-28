package com.example.hp.maopaonews.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Created by hp on 2016/1/14.
 */
public class HttpPostThread implements Runnable {
    private String name;
    private String password;
    private Handler hand;
    private Context context;
    private MyPost myPost;

    public HttpPostThread(Context context,String name, String password,Handler handler) {
        this.context=context;
        this.name=name;
        this.password=password;
        this.hand=handler;
        myPost=new MyPost(context);
    }

    @Override
    public void run() {
        Message msg=hand.obtainMessage();
        boolean isnull=myPost.doPost(name,password);
        msg.what = 200;
        msg.obj = isnull;
        hand.sendMessage(msg);
    }
}
