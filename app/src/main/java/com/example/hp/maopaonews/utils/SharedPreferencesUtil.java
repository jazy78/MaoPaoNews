package com.example.hp.maopaonews.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hp on 2016/1/8.
 */
public class SharedPreferencesUtil {
    private static  String CONFIG="config";
    private static SharedPreferences sp;

    public static  void SaveData(Context context,String key,String data){
        if(sp==null){
            sp=context.getSharedPreferences(CONFIG,Context.MODE_PRIVATE);

        }
        sp.edit().putString(key,data).commit();


    }

    public static  String GetDate(Context context,String key,String defValues){

        if(sp==null){
            sp=context.getSharedPreferences(CONFIG,Context.MODE_PRIVATE);

        }
        return  sp.getString(key,defValues);
    }




}
