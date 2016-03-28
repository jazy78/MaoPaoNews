package com.example.hp.maopaonews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by hp on 2016/1/14.
 */
public class QieYuanTu {
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
               Canvas canvas = new Canvas(output);
        canvas.drawARGB(0, 0, 0, 0);
        final Paint paint = new Paint();

       /* final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        canvas.drawOval(rectF, paint);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,rect,rect,paint);*/
        Shader shader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Shape shape=new OvalShape();
        ShapeDrawable drawable=new ShapeDrawable(shape);
        drawable.getPaint().setShader(shader);
        drawable.getPaint().setAntiAlias(true);
       // drawable.getPaint().setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        drawable.setBounds(0,0,bitmap.getWidth(),bitmap.getHeight());
        drawable.draw(canvas);
        bitmap.recycle();
        return output;
    }

}
