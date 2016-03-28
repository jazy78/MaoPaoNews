package com.example.hp.maopaonews.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hp on 2016/1/13.
 */
public class Md5Utils {
    public static  final  String encodeBy32BitMD5(String name){

        return encrypt(name, false);
    }

    private static  final  String encrypt(String source, boolean is16bit){

        if (TextUtils.isEmpty(source)) {
            return null;
        }
        String encryptedStr = null;
        try{
            MessageDigest digest=MessageDigest.getInstance("MD5");
            byte[] date=digest.digest(source.getBytes("utf-8"));
             encryptedStr=convertToHexString(date);
            if(is16bit){

                encryptedStr=encryptedStr.substring(8,24);
            }

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
         e.printStackTrace();
        }

        return  encryptedStr;
    }


    private static final  String convertToHexString(byte[] data){
        StringBuffer buf = new StringBuffer();
        int i;
        for(int offser=0;offser<data.length;offser++){
            i=data[offser];
            if(i<0){
                i+=256;
            }
            if(i<16){
                buf.append("0");

            }
            buf.append(Integer.toHexString(i));
        }
        return  buf.toString();
    }

}
