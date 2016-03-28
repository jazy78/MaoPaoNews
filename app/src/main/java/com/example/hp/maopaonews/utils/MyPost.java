package com.example.hp.maopaonews.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * Created by hp on 2016/1/14.
 */
public class MyPost {
    MySqlOpenHelper mySqlOpenHelper;
    Context context;

    public MyPost(Context context) {
        this.context = context;
    }

    public boolean doPost(String name, String password) {
        mySqlOpenHelper = new MySqlOpenHelper(context);
        String names = Md5Utils.encodeBy32BitMD5(name);
        String passwords = Md5Utils.encodeBy32BitMD5(password);
        SQLiteDatabase db = null;

        try {
            db = mySqlOpenHelper.getWritableDatabase();
            Cursor cursor = db.query("login_date", null, "name =?", new String[]{names}, null, null, null, null);

            while (cursor.moveToNext()) {
                String string = cursor.getString(cursor.getColumnIndex("password"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                if (passwords.equals(string)) {
                    context.getSharedPreferences("useInfo", Context.MODE_PRIVATE).edit()
                            .putString("userName", name).commit();
                    if (url != null) {
                        context.getSharedPreferences("useInfo", Context.MODE_PRIVATE).edit().putString("pic_path", url).commit();

                    }
                    cursor.close();
                    db.close();
                    mySqlOpenHelper.close();
                    return true;

                }
            }
            cursor.close();
            db.close();
            mySqlOpenHelper.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
    ///////
}
