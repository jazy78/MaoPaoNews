package com.example.hp.maopaonews.Setting_utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.hp.maopaonews.R;

import org.w3c.dom.Text;

/**
 * Created by hp on 2016/1/11.
 */
public class ZiTiScale {

    public static void zitiStyle(final Context context, WebView webView) {
        final WebSettings settings = webView.getSettings();
        final SharedPreferences sharedPreferences = context.getSharedPreferences("hgz", Context.MODE_APPEND);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        String string = sharedPreferences.getString("teda1", null);
        if (string == null) {
             settings.setSupportZoom(true);
            settings.setBuiltInZoomControls(true);
            settings.setTextSize(WebSettings.TextSize.SMALLER);
        }else {
            switch (string){
                case "teda":
                    settings.setSupportZoom(true);
                    settings.setBuiltInZoomControls(true);
                    settings.setTextSize(WebSettings.TextSize.LARGEST);
                case "dahaozi":
                    settings.setSupportZoom(true);
                    settings.setBuiltInZoomControls(true);
                    settings.setTextSize(WebSettings.TextSize.LARGER);
                    break;
                case "zhonghaozi":
                    settings.setSupportZoom(true);
                    settings.setBuiltInZoomControls(true);
                    settings.setTextSize(WebSettings.TextSize.NORMAL);
                    break;
                case "xiaohaozi":
                    settings.setSupportZoom(true);
                    settings.setBuiltInZoomControls(true);
                    settings.setTextSize(WebSettings.TextSize.SMALLER);
                    break;
            }


        }


    }


    public static void zitiStyle2(final Context context, WebView webView) {

        final WebSettings setting = webView.getSettings();
        final SharedPreferences sharedPreferences = context.getSharedPreferences("hgz", Context.MODE_APPEND);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("修改昵称");
        View view = LayoutInflater.from(context).inflate(R.layout.hgz_ziti_scale, null);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //设置添加方法
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //添加方法;
            }
        });
        builder.create();
        builder.show();
        final TextView teda = (TextView) view.findViewById(R.id.teda);
        final TextView dahaozi = (TextView) view.findViewById(R.id.dahaozi);
        final TextView zhonghaozi = (TextView) view.findViewById(R.id.zhonghaozi);
        final TextView xiaohaozi = (TextView) view.findViewById(R.id.xiaohaozi);
        teda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teda.setTextColor(Color.YELLOW);
                setting.setSupportZoom(true);
                setting.setBuiltInZoomControls(true);
                setting.setTextSize(WebSettings.TextSize.LARGEST);
                editor.putString("teda1", "teda");
                editor.commit();
            }
        });
        dahaozi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dahaozi.setTextColor(Color.YELLOW);
                setting.setSupportZoom(true);
                setting.setBuiltInZoomControls(true);
                setting.setTextSize(WebSettings.TextSize.LARGER);
                editor.putString("teda1", "dahaozi");
                editor.commit();
            }
        });
      zhonghaozi.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              zhonghaozi.setTextColor(Color.YELLOW);
              setting.setSupportZoom(true);
              setting.setBuiltInZoomControls(true);
              setting.setTextSize(WebSettings.TextSize.NORMAL);
              editor.putString("teda1", "zhonghaozi");
              editor.commit();
          }
      });
    xiaohaozi.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            xiaohaozi.setTextColor(Color.YELLOW);
            setting.setSupportZoom(true);
            setting.setBuiltInZoomControls(true);
            setting.setTextSize(WebSettings.TextSize.SMALLER);
            editor.putString("teda1", "xiaohaozi");
            editor.commit();
        }
    });
    }

}
