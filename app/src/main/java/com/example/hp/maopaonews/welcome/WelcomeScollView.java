package com.example.hp.maopaonews.welcome;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by hp on 2016/1/6.
 */
public class WelcomeScollView extends ScrollView {
    public ScrollViewListener scrollViewListener;
    public WelcomeScollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WelcomeScollView(Context context) {
        super(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(scrollViewListener!=null){
            scrollViewListener.scrollViewListener(t,oldt);
        }
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener){

        this.scrollViewListener=scrollViewListener;
    }
}
