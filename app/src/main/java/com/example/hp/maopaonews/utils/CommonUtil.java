package com.example.hp.maopaonews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hp on 2016/1/6.
 */
public class CommonUtil {

    public static boolean isNetWork(Context context){

        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if(info!=null){

            return  true;
        }else {
            return false;
        }
  }



    public static boolean isNetwork1(Context context){

        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] infosAll=manager.getAllNetworkInfo();
        for(int i=0;i<infosAll.length;i++){

            if(infosAll[i]!=null &&infosAll[i].getState()== NetworkInfo.State.CONNECTED){

                return true;
            }

        }
        return  false;
     }
    //日期转化为字符串
    public static  String getStringData() {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(date);
        return dateString;

    }
    //字符串转化为data

    public Date getsimpledate(){

        String str="2016-1-8";
        Date date=new Date(str);
        return  date;
    }


    public Date getformatdate(){
        String str="2016-1-8";
        SimpleDateFormat format=new SimpleDateFormat("yy-MM-dd  HH-mm-ss");
        try {
            Date date=format.parse(str);
            return  date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return  null;
    }


}
