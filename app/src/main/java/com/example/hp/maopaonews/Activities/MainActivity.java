package com.example.hp.maopaonews.Activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.example.hp.maopaonews.BroadReceiver.MyReceiver;
import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.content_fragment.ReDianFrament;
import com.example.hp.maopaonews.content_fragment.SheZhiFramen;
import com.example.hp.maopaonews.content_fragment.ShiTingFrament;
import com.example.hp.maopaonews.content_fragment.XinWenFragment;
import com.example.hp.maopaonews.content_fragment.YueDuFrament;
import com.example.hp.maopaonews.utils.CommonUtil;
import com.example.hp.maopaonews.viewpager.ContentViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ContentViewPager contentViewPager;
    private RadioGroup contentradiogroup;
    private MyReceiver myReceiver;
    private List<Fragment> content_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        InstanceActivtiyApplication();
        //判断是否连网
        checkNetState();

        //定义的广播只接受固定的广播信息
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver=new MyReceiver();
        registerReceiver(myReceiver,intentFilter);
        initdata();
        initview();

    }

    private void initdata(){
        content_list=new ArrayList<>();
        content_list.add(new XinWenFragment());
        content_list.add(new ReDianFrament());
        content_list.add(new ShiTingFrament());
        content_list.add(new YueDuFrament());
        content_list.add(new SheZhiFramen());

    }

    private void initview(){
        if(content_list==null){
            return;
        }
        contentViewPager=(ContentViewPager)findViewById(R.id.content_viewpager);
        contentradiogroup = (RadioGroup) findViewById(R.id.content_radiogroup);
        contentViewPager.setOffscreenPageLimit(2);
        contentViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return content_list.get(position);
            }

            @Override
            public int getCount() {
                return content_list==null?0:content_list.size();
            }
        });
    contentradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                case R.id.rb_xinwen:
                    contentViewPager.setCurrentItem(0);
                    break;
                case R.id.rb_redian:
                    contentViewPager.setCurrentItem(1);
                    break;
                case R.id.rb_shiting:
                    contentViewPager.setCurrentItem(2);
                    break;
                case R.id.rb_yuedu:
                    contentViewPager.setCurrentItem(3);
                    break;
                case R.id.rb_shezhi:
                    contentViewPager.setCurrentItem(4);
                    break;
            }
        }
      });
        //默认选择第一页
        contentradiogroup.check(R.id.rb_xinwen);

    }




    /**
     *
     * 检查网络的状态
     */

    private void checkNetState(){
        if(!CommonUtil.isNetWork(this)){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("网络提醒");
            builder.setMessage("当前网络不可用，是否打开网络");

            builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Snackbar.make(findViewById(R.id.content_radiogroup),"没网咋用",Snackbar.LENGTH_LONG)
                            .setAction("知道了", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                }
            });

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(Build.VERSION.SDK_INT>10){
                        //启动手机的设置Activity
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }else {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    }
                }
            });
            builder.create().show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void InstanceActivtiyApplication(){

        ActivityManager activityManager= ActivityManager.getInstance();
        activityManager.addActivity(this);
    }
}
