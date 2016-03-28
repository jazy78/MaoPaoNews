package com.example.hp.maopaonews.JiePing;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

import java.io.FileOutputStream;

/**
 * Created by hp on 2016/1/11.
 */
public class ScreenShot {

    public static Bitmap takeScreenShot(Activity activity){
        // View是你须要截图的View
        View view=activity.getWindow().getDecorView();
        int width1=view.getWidth();
        int height1=view.getHeight();

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();//建立缓冲位图
        Bitmap b1=view.getDrawingCache();//获取缓冲位图

        Rect frame=new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusbarHeight=frame.top;

        Bitmap b=Bitmap.createBitmap(b1,0,statusbarHeight,width1,height1-statusbarHeight);
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return  b;
    }


    public static  void savePic(Bitmap b,String fileName){
        FileOutputStream fos=null;

        try{
            fos=new FileOutputStream(fileName);
            b.compress(Bitmap.CompressFormat.PNG,90,fos);
            fos.flush();
            fos.close();

        }catch ( Exception e){
            e.printStackTrace();

        }


    }

}
