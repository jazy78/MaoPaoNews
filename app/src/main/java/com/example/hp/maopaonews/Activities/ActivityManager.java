package com.example.hp.maopaonews.Activities;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/1/15.
 */
public class ActivityManager extends Application {

    private List<Activity> activityList = new ArrayList<>();

    private static ActivityManager instance;

    public static ActivityManager getInstance() {

        if (instance == null) {
            instance = new ActivityManager();

        }

        return instance;
    }


    public void addActivity(Activity activity) {

        activityList.add(activity);
    }

    public void finishAll() {
        for (Activity activity : activityList) {

            activity.finish();
        }
        //杀掉，这个应用程序的进程，释放 内存
        int id = android.os.Process.myPid();
        if (id != 0) {
            android.os.Process.killProcess(id);
        }
    }
}