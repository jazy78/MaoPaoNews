package com.example.hp.maopaonews.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.hp.maopaonews.R;

/**
 * Created by hp on 2016/1/18.
 */
public class Setting_glodmall extends AppCompatActivity {
    private ImageView backsetting;
    private WebView webView;
    private WebSettings settings;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_glodmall);

        initView();
        getData();
    }
    private void  getData(){

        settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setSupportZoom(true);
        settings.setSupportMultipleWindows(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setAppCacheEnabled(true);
        webView.loadUrl("http://www.hao123.com");
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return  true;


            }
        });

    }

    private void  initView(){
        backsetting=(ImageView)findViewById(R.id.backsetting);
        webView=(WebView)findViewById(R.id.webView);
        backsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.push_left_in1,R.anim.push_left_out1);
                finish();
            }
        });

    }
}
