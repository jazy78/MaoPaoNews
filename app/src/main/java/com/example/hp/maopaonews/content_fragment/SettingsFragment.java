package com.example.hp.maopaonews.content_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.example.hp.maopaonews.Activities.LoginActivity;
import com.example.hp.maopaonews.Activities.PrivateActivity;
import com.example.hp.maopaonews.R;

/**
 * Created by hp on 2016/1/14.
 */
public class SettingsFragment extends PreferenceFragment {
    static boolean isLoading = false;
    private Preference privateSetting;
    private static  SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        sharedPreferences=getActivity().getSharedPreferences("isLoading", Context.MODE_PRIVATE);

        isLoading=sharedPreferences.getBoolean("isLoading",false);
        Log.d("III", "SettingsFragment");
        privateSetting = (Preference) findPreference("private_set");

        initOnClick();

    }

    private void initOnClick() {


        privateSetting.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Log.d("KKK","isLoading222="+isLoading);
                if (isLoading) {

                    Intent intent1 = new Intent(getActivity(), PrivateActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }


                return true;
            }
        });
    }

    public static Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 1:
                    isLoading = true;
                    Log.d("KKK","isLoading111="+isLoading);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("isLoading",isLoading).commit();
                    break;
                case 2:
                    isLoading=false;
                    SharedPreferences.Editor editor1=sharedPreferences.edit();
                    editor1.putBoolean("isLoading",isLoading).commit();
            }

        }
    };
    public  static void setIsLoading(boolean Loading){
        isLoading=Loading;
        SharedPreferences.Editor editor1=sharedPreferences.edit();
        editor1.putBoolean("isLoading",isLoading).commit();
    }

    /////
}
