package com.example.hp.maopaonews.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.utils.CommonUtil;
import com.example.hp.maopaonews.utils.DateTime;
import com.example.hp.maopaonews.utils.MySqlOpenHelper;
import com.example.hp.maopaonews.utils.XinWenXi;
import com.example.hp.maopaonews.utils.XinWenXiData;
import com.example.hp.maopaonews.utils.XutilsGetData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/1/7.
 */
public class XinWenXiActivity extends AppCompatActivity {

    private TextView xinwencontent;
    private ViewPager imagePager;
    private XinWenXiData xinWenXiData;
    private TextView duotu_gentie;
    ImageButton caidan;
    ImageButton fenxiang;
    ImageButton imageback;
    TextView title;
    private XutilsGetData xutilsGetData = new XutilsGetData();
    private List<XinWenXi.PhotosObj> photoslist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InstanceActivtiyApplication();
        setContentView(R.layout.xinwen_xi_duotu_frament);
        Intent intent = getIntent();
        xinWenXiData = (XinWenXiData) intent.getSerializableExtra("xinwendata");
        initDate();
        initview();
    }
    private void InstanceActivtiyApplication(){

        ActivityManager activityManager= ActivityManager.getInstance();
        activityManager.addActivity(this);
    }
    public void initview() {
        final String url = xinWenXiData.getUrl();//获得详细页面的url      //分享用
        final String xinwentitle = xinWenXiData.getTitle();//获得新闻标题     //分享用
        imageback = (ImageButton) findViewById(R.id.xinwen_xi_back);
        duotu_gentie = (TextView) findViewById(R.id.xinwen_duotu_gentie);
        caidan = (ImageButton) findViewById(R.id.xinwen_xi_kuanzhan_caidan);
        fenxiang = (ImageButton) findViewById(R.id.xinwen_xi_fenxiang);
        imagePager = (ViewPager) findViewById(R.id.xinwenxi_viewpager);
        xinwencontent = (TextView) findViewById(R.id.xinwenxi_content);
        title = (TextView) findViewById(R.id.xinwen_xi_title);
        title.setText(xinwentitle);
        int replaycount = xinWenXiData.getReplaycount();
        if (replaycount == 0) {
            duotu_gentie.setText("");
            duotu_gentie.setBackgroundColor(Color.parseColor("#ff000000"));
        }
        duotu_gentie.setText(xinWenXiData.getReplaycount() + "跟帖");
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        duotu_gentie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        caidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getpopuwindow(v);
            }
        });

        getdata(url);

    }

    private void getdata(String url){
        if(CommonUtil.isNetWork(this)){
            xutilsGetData.xUtilsHttp(this, url, new XutilsGetData.CallBackHttp() {
                @Override
                public void handleData(String data) {
                    getshowData(data);
                }
            },true);

        }else {

            String data=xutilsGetData.getData(this,url,null);
            getshowData(data);
        }

    }

    private void getshowData(String data){

        int lanmuType=xinWenXiData.getLanMuType();
        photoslist=XinWenXi.getdata(data,this,lanmuType);
        String gentie = photoslist.get(0).getGentieUrl();//获得跟帖的URL
        ImagePagerAdapter adapter=new ImagePagerAdapter();
        imagePager.setAdapter(adapter);
        imagePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                xinwencontent.setText(photoslist.get(0).getPhotosList().get(position).getText());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class ImagePagerAdapter extends PagerAdapter{


        @Override
        public int getCount() {
            return photoslist.get(0).getPhotosList().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(photoslist.get(0).getPhotosList().get(position).getImg());

            if(position==0){
                xinwencontent.setText(photoslist.get(0).getPhotosList().get(position).getText());
            }
            return  photoslist.get(0).getPhotosList().get(position).getImg();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }


    private void getpopuwindow(View v) {
        final PopupWindow popu = new PopupWindow((int) (getWindowManager().getDefaultDisplay().getWidth() / 2.5), getWindowManager().getDefaultDisplay().getHeight() / 2);
        View view = View.inflate(this, R.layout.popwindow_detial, null);
        Button shouchang = (Button) view.findViewById(R.id.bt_save);
        Button jieping = (Button) view.findViewById(R.id.jieping);
        Button ziti = (Button) view.findViewById(R.id.ziti);
        Button yejian = (Button) view.findViewById(R.id.bt_yejian);
        shouchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popu.dismiss();
            }
        });
        jieping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popu.dismiss();
            }
        });
        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popu.dismiss();
            }
        });
        ziti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popu.dismiss();
            }
        });
        yejian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popu.dismiss();
            }
        });
        popu.setContentView(view);
        popu.setFocusable(true);
        popu.setBackgroundDrawable(new ColorDrawable(0));
        popu.showAsDropDown(v, 0, 0);
    }

    public void initDate() {
        String url = xinWenXiData.getUrl();//获得详细页面的url      //分享用
        String xinwentitle = xinWenXiData.getTitle();//获得新闻标题     //分享用
        int replaycount = xinWenXiData.getReplaycount();//获得跟帖数目  //收藏用

        //获得当前的日期
        String date = DateTime.getDate();
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(this);
        SQLiteDatabase db = mySqlOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("read_date", null, "date=?", new String[]{date}, null, null, null, null);
        if (cursor.getCount() > 0) {

            ArrayList<String> biaoti = new ArrayList<>();
            while (cursor.moveToNext()) {
                String cursorString = cursor.getString(cursor.getColumnIndex("title"));

                biaoti.add(cursorString);
            }

            if (!biaoti.contains(xinwentitle)) {
                ContentValues values = new ContentValues();
                values.put("date", date + "");
                values.put("url", url + "");
                values.put("title", xinwentitle + "");
                values.put("num", 2 + "");
                values.put("replaycount", replaycount + "");
                db.insert("read_date", null, values);
            }

        } else {
            ContentValues values = new ContentValues();
            values.put("date", date + "");
            values.put("url", url + "");
            values.put("title", xinwentitle + "");
            values.put("num", 2 + "");
            values.put("replaycount", replaycount + "");
            db.insert("read_date", null, values);
        }

        cursor.close();
        db.close();

    }


}
