package com.example.hp.maopaonews.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hp on 2016/1/8.
 */
public class MyHelper extends SQLiteOpenHelper {

    private static final String DATABASENAME = "newsSearch.db";
    private static int version = 1;

    public MyHelper(Context context) {
        super(context, DATABASENAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table searchHistory(_id integer primary key autoincrement,url varchar,searchWord varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
