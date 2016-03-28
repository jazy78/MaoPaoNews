package com.example.hp.maopaonews.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hp on 2016/1/13.
 */
public class Utils {
    public static boolean IsPhoneNumber(String s){
        Pattern p=Pattern.compile("1\\d{10}");
        Matcher m=p.matcher(s);
        boolean b=m.matches();
        return b;

    }

}
