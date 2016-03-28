package com.example.hp.maopaonews.Setting_utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hp on 2016/1/14.
 */
public class TouXiangCache {


    public static  void saveMyBitmap(Bitmap bitmap,String pic_pathload){
        File f=new File("storage/sdcard0/"+pic_pathload);
        try {
            FileOutputStream fout=new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fout);
            fout.flush();
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }


    }


    //从本地获得图片
    public static Bitmap getphoto(String path){

        Bitmap bitmap= BitmapFactory.decodeFile(path);
        return  bitmap;
    }

}
