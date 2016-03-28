package com.example.hp.maopaonews.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hp on 2016/1/6.
 */
public class ContentViewPager  extends ViewPager{

    public ContentViewPager(Context context) {
        super(context);
    }

    public ContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static  boolean GO_TOUCH_CHILD=true;


    ////////不拦截子view的触摸事件

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(GO_TOUCH_CHILD){
         //当前控件不拦截触屏事件
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(GO_TOUCH_CHILD){
            //当前控件不消费 触屏事件
            return  false;
      }

        return super.onTouchEvent(ev);
    }
}
