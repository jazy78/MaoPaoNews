package com.example.hp.maopaonews.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.maopaonews.JiePing.ScreenShot;
import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.Setting_utils.ShareUtils;
import com.example.hp.maopaonews.utils.DateTime;

import java.util.ArrayList;

/**
 * Created by hp on 2016/1/11.
 */
public class PictureActivity extends AppCompatActivity {
    private String path;
    private ArrayList<Drawable> arrayList;
    private ArrayList<String> stringList;
    private LinearLayout linearLayout;
    private ImageView imageShow;
    private TextView text;
    private String picpath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        InstanceActivtiyApplication();
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        if (path != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(path + ".png");
            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageBitmap(bitmap);

        } else {
            path = DateTime.getDate_Time();
        }
        if (arrayList == null) {
            arrayList = new ArrayList();
            arrayList.add(getResources().getDrawable(R.drawable.naodong_1));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_2));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_3));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_4));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_5));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_6));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_7));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_8));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_9));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_10));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_11));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_12));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_13));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_14));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_15));
        }

        if (stringList == null) {
            stringList = new ArrayList<>();
            stringList.add("我和我的小伙伴都惊呆了");
            stringList.add("你懂得");
            stringList.add("麻蛋,老子裤子都脱了,你给我看这个?");
            stringList.add("你好流弊啊");
            stringList.add("我读书少,表骗我");
            stringList.add("不明觉里");
            stringList.add("我去年买了个表啊");
            stringList.add("翻滚吧,牛宝宝");
            stringList.add("笑死老子了");
            stringList.add("知道真相的我眼泪流下来");
            stringList.add("感觉不会再爱了");
            stringList.add("看我的手势...");
            stringList.add("静静的看你装13");
            stringList.add("2到无穷大");
            stringList.add("分分钟,又涨姿势了");
        }

        linearLayout = (LinearLayout) findViewById(R.id.parentview);
        imageShow = (ImageView) findViewById(R.id.imageShow);
        text = (TextView) findViewById(R.id.text);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onClick(View v) {
        ImageView imageView=(ImageView)linearLayout.getTag();
        if(imageView!=null){
            imageView.setBackground(getResources().getDrawable(R.drawable.touming));
        }
         switch(v.getId()){
             case R.id.image1:
                 imaSetBack(0, v);
                 break;
             case R.id.image2:
                 imaSetBack(1, v);
                 break;
             case R.id.image3:
                 imaSetBack(2, v);
                 break;
             case R.id.image4:
                 imaSetBack(3, v);
                 break;
             case R.id.image5:
                 imaSetBack(4, v);
                 break;
             case R.id.image6:
                 imaSetBack(5, v);
                 break;
             case R.id.image7:
                 imaSetBack(6, v);
                 break;
             case R.id.image8:
                 imaSetBack(7, v);
                 break;
             case R.id.image9:
                 imaSetBack(8, v);
                 break;
             case R.id.image10:
                 imaSetBack(9, v);
                 break;
             case R.id.image11:
                 imaSetBack(10, v);
                 break;
             case R.id.image12:
                 imaSetBack(11, v);
                 break;
             case R.id.image13:
                 imaSetBack(12, v);
                 break;
             case R.id.image14:
                 imaSetBack(13, v);
                 break;
             case R.id.image15:
                 imaSetBack(14, v);
                 break;
             case R.id.loginimage_share:
                 Bitmap bitmap1= ScreenShot.takeScreenShot(this);
                 picpath=path+"涂鸦.png";
                 ScreenShot.savePic(bitmap1,picpath);
                handler.sendEmptyMessageAtTime(1,500);
         }

    }

    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ShareUtils.shareContent(PictureActivity.this, "跟帖截屏,值得一看哦!", picpath);
           // Toast.makeText(getApplicationContext(),"由于没有获得分享平台的正确资源ID,分享暂时不能用",Toast.LENGTH_LONG).show();
        }
    };

    @TargetApi(Build.VERSION_CODES.KITKAT)
    void imaSetBack(int num,View view){
        view.setBackground(getResources().getDrawable(R.drawable.titlebar_background));
        linearLayout.setTag(view);
        imageShow.setBackground(arrayList.get(num));
        text.setText(stringList.get(num));

    }

    private void InstanceActivtiyApplication(){

        ActivityManager activityManager= ActivityManager.getInstance();
        activityManager.addActivity(this);
    }
    ////
}
