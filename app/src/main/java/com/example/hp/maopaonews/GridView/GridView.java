package com.example.hp.maopaonews.GridView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.hp.maopaonews.R;

/**
 * Created by hp on 2016/1/19.
 */
public class GridView  extends android.widget.GridView {

    public GridView(Context context) {
        super(context);
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
             super.dispatchDraw(canvas);
        View localView1=getChildAt(0);
        int width=localView1.getWidth();
        int height=localView1.getHeight();
        int column=getWidth()/localView1.getWidth();
        int childCount=getChildCount();
        int index=0;
        if(childCount%column==0){
            index=childCount/column;

        }else {

            index=childCount/column+1;
        }
        Paint localPaint=new Paint();
        localPaint.setColor(getResources().getColor(R.color.black_qian));
        for(int i=0;i<childCount;i++){
            View cellview=getChildAt(i);
            if((i+1)%column==0){
               canvas.drawLine(cellview.getLeft(),cellview.getBottom(),cellview.getRight(),cellview.getBottom(),localPaint);
            } else {

                canvas.drawLine(cellview.getRight(), cellview.getTop(), cellview.getRight(), cellview.getBottom(), localPaint);
                canvas.drawLine(cellview.getLeft(), cellview.getBottom(), cellview.getRight(), cellview.getBottom(), localPaint);
            }

           /* if(childCount % column != 0){
                for(int j = 0 ;j < (column-childCount % column) ; j++){
                    View lastView = getChildAt(childCount - 1);
                    canvas.drawLine(lastView.getRight() + lastView.getWidth() * j, lastView.getTop(), lastView.getRight() + lastView.getWidth()* j, lastView.getBottom(), localPaint);
                }
            }*/

               /* for(int j=childCount;j<index*column;j++){

                    if((j+1)%column==0){

                        canvas.drawLine((column-1)*width,index*height,column*width,index*height,localPaint);
                    }else {
                        canvas.drawLine((j%column+1)*width,(index-1)*height,(j%column+1)*width,index*height,localPaint);
                        canvas.drawLine((j%column)*width,index*height,(j%column+1)*width,index*height,localPaint);
                    }

                }*/
        }

    }
}
