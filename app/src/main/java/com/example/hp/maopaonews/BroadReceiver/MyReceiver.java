package com.example.hp.maopaonews.BroadReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by hp on 2016/1/6.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            Toast.makeText(context, "检测到当前并未连接网络......", Toast.LENGTH_SHORT).show();
        } else if (wifiNetInfo.isConnected()) {
            Toast.makeText(context, "正在使用wifi连接,请放心使用......", Toast.LENGTH_SHORT).show();
        } else if (mobNetInfo.isConnected()) {
            Toast.makeText(context, "检测到正在使用移动网络,可能会消耗很多流量,建议在wifi下浏览.....", Toast.LENGTH_SHORT).show();
        }
    }
}
