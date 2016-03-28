package com.example.hp.maopaonews.content_fragment;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.maopaonews.Activities.MainActivity;
import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.Setting_utils.SearchDB;

/**
 * Created by hp on 2016/1/15.
 */
public class Private_fragment extends PreferenceFragment{

    private Preference quit;
    private LinearLayout quit_account;
    private LinearLayout quit_all;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferenceset);
        quit=(Preference)findPreference("quitting");
        initOnClick();
    }

    private void initOnClick(){

        quit.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                final View view= LayoutInflater.from(getActivity()).inflate(R.layout.quitting,null);
                quit_account=(LinearLayout) view.findViewById(R.id.quit_account);
                quit_all=(LinearLayout)view.findViewById(R.id.quit_all);
                quit_account.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SearchDB.removeDb(getActivity().getSharedPreferences("useInfo", Context.MODE_PRIVATE));
                        Message message=new Message();
                        message.what=1;
                        SheZhiFramen.handler.sendMessage(message);
                        SettingsFragment.setIsLoading(false);
                        getActivity().finish();
                    }
                });

                quit_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        com.example.hp.maopaonews.Activities.ActivityManager manager= com.example.hp.maopaonews.Activities.ActivityManager.getInstance();
                        manager.finishAll();
                        Log.d("TTT","我到了");
                    }
                });

                builder.setView(view);
                builder.create();
                builder.show();
                return true;
            }
        });

    }


}
