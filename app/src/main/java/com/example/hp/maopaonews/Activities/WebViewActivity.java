package com.example.hp.maopaonews.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.hp.maopaonews.CostomProgressDialog.CustomProgressDialog;
import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.utils.DateTime;
import com.example.hp.maopaonews.utils.MySqlOpenHelper;
import com.example.hp.maopaonews.utils.XinWenXiData;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by hp on 2016/1/7.
 */
public class WebViewActivity extends AppCompatActivity {
    private XinWenXiData xinWenXiData;
    private WebView webView;
    private WebSettings settings;
    ImageButton fenxiang;
    ImageButton imageback;
    TextView duotu_gentie;
    ImageButton caidan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.xinwen_xi_putong_frament);
        InstanceActivtiyApplication();
        Intent intent = getIntent();
        xinWenXiData = (XinWenXiData) intent.getSerializableExtra("xinwendata");
        initDate();
        initview();

    }
    private void InstanceActivtiyApplication(){

        ActivityManager activityManager= ActivityManager.getInstance();
        activityManager.addActivity(this);
    }
    private void initview() {
        final String url = xinWenXiData.getUrl();//获得详细页面的url      //分享用
        final String xinwentitle = xinWenXiData.getTitle();//获得新闻标题     //分享用
        imageback = (ImageButton) findViewById(R.id.xinwen_xi_back);//返回
        duotu_gentie = (TextView) findViewById(R.id.xinwen_duotu_gentie);//跟帖
        fenxiang = (ImageButton) findViewById(R.id.xinwen_xi_fenxiang);
        caidan = (ImageButton) findViewById(R.id.xinwen_xi_kuanzhan_caidan);//菜单
        webView = (WebView) findViewById(R.id.xinwen_xi_text_webview);
        duotu_gentie.setText(xinWenXiData.getReplaycount() + "跟帖");
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        caidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getpopuwindow(v);
            }
        });

        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });


        getdata(url);
    }

    public void getdata(String url){
        settings=webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setNeedInitialFocus(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setAppCacheEnabled(true);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        webView.setWebChromeClient(new WebChromeClient());// 支持运行特殊的javascript(例如：alert())
        final CustomProgressDialog progressDialog=new CustomProgressDialog(this,"正在加载中......",R.anim.animation_frame);
        progressDialog.show();
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return  true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }
        });

    }

    private void getpopuwindow(View v) {
        Display display=getWindowManager().getDefaultDisplay();
           final PopupWindow popu=new PopupWindow((int)(display.getWidth()/2.5),display.getHeight()/2);
          View view=View.inflate(this,R.layout.popwindow_detial,null);
        Button shouchang = (Button) view.findViewById(R.id.bt_save);
        Button jieping = (Button) view.findViewById(R.id.jieping);
        Button ziti = (Button) view.findViewById(R.id.ziti);
        Button yejian = (Button) view.findViewById(R.id.bt_yejian);
        shouchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popu.dismiss();
                // TODO: 2015/11/17

            }
        });
        jieping.setOnClickListener(new View.OnClickListener() {
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
        popu.showAsDropDown(v,0,0);
    }


    public void initDate() {

        String url = xinWenXiData.getUrl();//获得详细页面的url      //分享用
        String xinwentitle = xinWenXiData.getTitle();//获得新闻标题     //分享用
        int replaycount = xinWenXiData.getReplaycount();//获得跟帖数目  //收藏用
        int lanMuType = xinWenXiData.getLanMuType();//获得跟帖数目  //收藏用
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
