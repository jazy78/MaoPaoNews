package com.example.hp.maopaonews.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.maopaonews.CostomProgressDialog.CustomProgressDialog;
import com.example.hp.maopaonews.JiePing.ScreenShot;
import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.Setting_utils.ShareUtils;
import com.example.hp.maopaonews.Setting_utils.ZiTiScale;
import com.example.hp.maopaonews.utils.DateTime;
import com.example.hp.maopaonews.utils.MySqlOpenHelper;
import com.example.hp.maopaonews.utils.ServerURL;
import com.example.hp.maopaonews.utils.SharedPreferencesUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by hp on 2016/1/9.
 */
public class YueDuDetialActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton back;
    private TextView title_gentie;
    private ImageButton rightmore_content;
    private RelativeLayout scroll;
    private TextView tv_title;
    private TextView msource;
    private TextView mptime;
    private ImageView iv;
    private WebView webView;
    private ImageButton iv_writeGenTie;
    private TextView tv_writeGenTie;
    private ImageButton replyNum;
    private TextView tv_num;
    private ImageButton share;
    private WebSettings settings;
    private String DetialUrl;//阅读详情页URL
    private HttpUtils httpUtils;
    private String replyid;
    private CustomProgressDialog progressDialog;
    private LinearLayout fiend;
    private LinearLayout qqzone;
    private LinearLayout qqfriend;
    private LinearLayout more;

    View popwindow_more;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuedu_detial);
        InstanceActivtiyApplication();
        initView();
        ZiTiScale.zitiStyle(this, webView);
    }
    private void InstanceActivtiyApplication(){

        ActivityManager activityManager= ActivityManager.getInstance();
        activityManager.addActivity(this);
    }
    private void initView() {
        progressDialog = new CustomProgressDialog(this, "正在加载中......", R.anim.animation_frame);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
        title_gentie = (TextView) findViewById(R.id.title_gentie);
        rightmore_content = (ImageButton) findViewById(R.id.rightmore_content);

        scroll = (RelativeLayout) findViewById(R.id.scroll);
        tv_title = (TextView) findViewById(R.id.tv_title);
        msource = (TextView) findViewById(R.id.source);
        mptime = (TextView) findViewById(R.id.ptime);
        iv = (ImageView) findViewById(R.id.iv);
        webView = (WebView) findViewById(R.id.webView);
        iv_writeGenTie = (ImageButton) findViewById(R.id.iv_writeGenTie);
        tv_writeGenTie = (TextView) findViewById(R.id.tv_writeGenTie);
        replyNum = (ImageButton) findViewById(R.id.replyNum);
        tv_num = (TextView) findViewById(R.id.tv_num);
        share = (ImageButton) findViewById(R.id.share);
        settings = webView.getSettings();
        fiend = (LinearLayout) findViewById(R.id.pengypuquan);
        fiend.setOnClickListener(this);
        qqfriend = (LinearLayout) findViewById(R.id.qq);
        qqfriend.setOnClickListener(this);
        qqzone = (LinearLayout) findViewById(R.id.qq_zone);
        qqzone.setOnClickListener(this);
        more = (LinearLayout) findViewById(R.id.more);
        more.setOnClickListener(this);
        share.setOnClickListener(this);


        popwindow_more = View.inflate(this, R.layout.popwindow_detial, null);
        rightmore_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopWindow(v);
            }
        });

        Intent intent = getIntent();
        replyid = intent.getStringExtra("yueduDetial");
        DetialUrl = ServerURL.yueDuDetial + replyid + ServerURL.yueDuHouzui;


        inintData();
    }

    private void inintData() {
        //共享参数缓存，首先从缓存中获取数据
        String result = SharedPreferencesUtil.GetDate(this, DetialUrl, "");
        if (!TextUtils.isEmpty(result)) {
            paserData(result);
            return;
        }
        getData(DetialUrl);
    }

    private HttpHandler<String> handler;

    private void getData(final String url) {
        if (!url.equals("")) {

            httpUtils = new HttpUtils();
            progressDialog.show();
            handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    if (responseInfo.result != null) {
                        SharedPreferencesUtil.SaveData(YueDuDetialActivity.this, url, responseInfo.result);
                        paserData(responseInfo.result);
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(YueDuDetialActivity.this, "数据请求失败,请检查网络设置...", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

        } else {
            Toast.makeText(this, "链接地址有误.........", Toast.LENGTH_SHORT).show();


        }
    }

    String replyCount;//跟帖数
    String title;
    String body;
    String src = null;

    private void paserData(String result) {


        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONObject object1 = jsonObject.getJSONObject(replyid);
            JSONArray jsonArray = object1.getJSONArray("img");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                src = jo.getString("src");

                body = object1.getString("body");
                String ptime = object1.getString("ptime");
                replyCount = object1.getString("replyCount");
                String source = object1.getString("source");
                title = object1.getString("title");

                tv_title.setText(title);//标题
                msource.setText(source);//来源
                mptime.setText(ptime);//时间
                title_gentie.setText(replyCount + " 跟帖");//跟帖数
                title_gentie.setTextSize(15);
                title_gentie.setTextColor(Color.WHITE);
                tv_num.setText(replyCount);
                webView.loadData(body, "text/html;charset=UTF-8", null);//具体内容
                BitmapUtils bitmapUtils = new BitmapUtils(this);
                bitmapUtils.display(iv, src);
                scroll.setVisibility(View.VISIBLE);//设置布局可见
                progressDialog.dismiss();
                initDate();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void initDate() {
//        String url = xinWenXiData.getUrl();//获得详细页面的url      //分享用
//        String xinwentitle = xinWenXiData.getTitle();//获得新闻标题     //分享用
//        int replaycount = xinWenXiData.getReplaycount();//获得跟帖数目  //收藏用
//        Log.e("aa", "******xinwentitle*******" + xinwentitle);
        //拿到当前日期
        String date = DateTime.getDate();
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(this);
        SQLiteDatabase writableDatabase = mySqlOpenHelper.getWritableDatabase();
        //查询数据库  当前日期 有无存储过 本页的标题
        Cursor cursor = writableDatabase.query("read_date", null, "date =?",
                new String[]{date}, null, null, null, null);
        //有没有当天的数据
        if (cursor.getCount() > 0) {
            ArrayList<String> biaoti = new ArrayList<>();//声明一个集合,用来存放遍历出来的标题
            while (cursor.moveToNext()) {   //遍历  拿到当天的 所有存储的标题
                String cursorString = cursor.getString(cursor.getColumnIndex("title"));
                biaoti.add(cursorString);
            }
            //当天数据中没有 本页的标题
            if (!biaoti.contains(title)) {
                ContentValues values = new ContentValues();
                values.put("date", date + "");
                values.put("url", replyid + "");
                values.put("title", title + "");
                values.put("num", 1 + "");
//            values.put("url",url);存储详情页的地址  在 阅读记录里取出来
                writableDatabase.insert("read_date", null, values);
            }
        } else {
            ContentValues values = new ContentValues();
            values.put("date", date + "");
            values.put("url", replyid + "");
            values.put("title", title + "");
            values.put("num", 1 + "");
            writableDatabase.insert("read_date", null, values);
        }
        //关闭
        cursor.close();
        writableDatabase.close();
    }

    PopupWindow popupWindow;
    Button bt_save;
    Button jieping;
    Button ziti;
    Button bt_yejian;

    private void initPopWindow(View view) {
        int width = (int) (getWindowManager().getDefaultDisplay().getWidth() / 2.5);
        int height = getWindowManager().getDefaultDisplay().getHeight() / 2;
        popupWindow = new PopupWindow(width, height);
        popupWindow.setContentView(popwindow_more);

        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.showAsDropDown(view, 0, 0);

        bt_save = (Button) popwindow_more.findViewById(R.id.bt_save);
        jieping = (Button) popwindow_more.findViewById(R.id.jieping);
        ziti = (Button) popwindow_more.findViewById(R.id.ziti);
        bt_yejian = (Button) popwindow_more.findViewById(R.id.bt_yejian);
        bt_save.setOnClickListener(this);
        jieping.setOnClickListener(this);
        ziti.setOnClickListener(this);
        bt_yejian.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.share:
                ShareUtils.shareContent(this, title, src);
                break;
            case R.id.pengypuquan:
                 ShareUtils.shareWeiXin(this,title,src);
                break;
            case R.id.qq:
                ShareUtils.shareQQFriend(this,title,src);

                break;
            case R.id.qq_zone:
                 // ShareUtils.shareQQZore(this,title,src);
                break;
            case R.id.more:
                 ShareUtils.shareContent(this,title,src);
                break;
            case R.id.ziti:
                popupWindow.dismiss();
                ZiTiScale.zitiStyle2(this, webView);
                break;
            case R.id.jieping:
                popupWindow.dismiss();
                Toast.makeText(this,"正在截屏....",Toast.LENGTH_LONG).show();;
                String date_time=DateTime.getDate_Time();
                File file=new File("sdcard/Photo/Screenshots/");
                if(!file.exists()){
                    file.mkdirs();
                }
                Bitmap bitmap= ScreenShot.takeScreenShot(this);
                String  s="sdcard/Photo/Screenshots/" + date_time ;
                String paths=s+".png";
                ScreenShot.savePic(bitmap,paths);
                Intent intent=new Intent(this,PictureActivity.class);
                intent.putExtra("path",s);
                startActivity(intent);

             break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (httpUtils != null) {

            handler.cancel();
        }
    }
}
