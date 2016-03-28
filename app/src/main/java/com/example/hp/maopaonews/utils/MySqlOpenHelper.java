package com.example.hp.maopaonews.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hp on 2016/1/7.
 */
public class MySqlOpenHelper extends SQLiteOpenHelper {

    private static  final  String DATEBASENAME="Login.db";
    private static  int version=1;

    public MySqlOpenHelper(Context context) {
        super(context, DATEBASENAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table login_date( _id integer primary key autoincrement, name varchar(36), password varchar(36),url varchar(36))");
        db.execSQL("create table read_date( _id integer primary key autoincrement, date varchar(36), " +
                "num varchar(36), title varchar(36), url varchar(36),replaycount varchar(36),lanMuType varchar(36))");
        Log.d("db","数据库创建了");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
