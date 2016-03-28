package com.example.hp.maopaonews.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hp on 2016/1/6.
 */
public class TouTiaoViewPager extends ViewPager {

    public static boolean GO_TOUTH_CHILD=true;
    public TouTiaoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouTiaoViewPager(Context context) {
        super(context);
    }

    //当我执行我的触摸事件时父控件不能对我进行干扰


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(GO_TOUTH_CHILD){
        getParent().requestDisallowInterceptTouchEvent(true);
        super.onTouchEvent(ev);

        return true;}

        return  super.onTouchEvent(ev);
    }
}
