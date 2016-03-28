package com.example.hp.maopaonews.Setting_utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hp on 2016/1/13.
 */
public class SearchDB {

    public static  String createDb(Context context,String user_Name){
        String user_name=null;
        SharedPreferences preferences=context.getSharedPreferences("useInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        String name_email = preferences.getString("userName", null);
        if(name_email!=null){

            user_name= "m" + name_email + "_1@163.com";
            return  user_name;
        }
        return  user_name;
    }

    public static  String TouXiangDb(Context context,String pic_Path) {
        String pic_Pathload = null;
        SharedPreferences preferences = context.getSharedPreferences("useInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String pic_path = preferences.getString("pic_path", null);
        if (pic_path != null) {
            pic_Pathload = pic_path;
            String pic_pathload = "storage/sdcard0/" + pic_Pathload;
            return pic_pathload;
        }
        return pic_Pathload;
    }

    public static void removeDb( SharedPreferences sharedPreferences){
          sharedPreferences.edit().remove("userName").commit();


    }
}
