package com.example.hp.maopaonews.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.utils.Md5Utils;
import com.example.hp.maopaonews.utils.MySqlOpenHelper;
import com.example.hp.maopaonews.utils.Utils;

/**
 * Created by hp on 2016/1/13.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView loginimage_back;
    EditText login_zhanghao,login_passwoed;
    TextView registerbutton,direct_login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InstanceActivtiyApplication();
        initView();

    }


    private void initView(){
        loginimage_back = (ImageView) findViewById(R.id.loginimage_back);
        loginimage_back.setOnClickListener(this);
        login_zhanghao = (EditText) findViewById(R.id.login_zhanghao);
        login_zhanghao.setOnClickListener(this);
        login_passwoed = (EditText) findViewById(R.id.login_passwoed);
        login_passwoed.setOnClickListener(this);
        registerbutton = (TextView) findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(this);
        direct_login=(TextView)findViewById(R.id.direct_login);
        direct_login.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.loginimage_back:
                finish();
                break;
            case R.id.registerbutton:
                String name = login_zhanghao.getText().toString().trim();
                String password = login_passwoed.getText().toString().trim();
                if ("".equals(name) || "".equals(password)) {
                    Toast.makeText(this, "请先注册...", Toast.LENGTH_SHORT).show();
                    break;
                }

                //是否是手机号
                  if(Utils.IsPhoneNumber(name)){
                      MySqlOpenHelper mySqlOpenHelper=new MySqlOpenHelper(this);
                      SQLiteDatabase db=mySqlOpenHelper.getWritableDatabase();
                      String names= Md5Utils.encodeBy32BitMD5(name);
                      String passwords = Md5Utils.encodeBy32BitMD5(password);
                      Cursor cursor = db.query("login_date", null, "name =?", new String[]{names}, null, null, null, null);

                      if(cursor.moveToNext()){
                          Toast.makeText(this, "亲,这个号码注册过了,赶快登陆去吧", Toast.LENGTH_SHORT).show();

                      }else {
                          ContentValues values = new ContentValues();
                          values.put("name", names);
                          values.put("password", passwords);
                          long login_date = db.insert("login_date", null, values);
                          if(login_date>0){
                              finish();
                              Toast.makeText(this, "注册成功了,赶快登陆去吧", Toast.LENGTH_SHORT).show();

                              getSharedPreferences("useInfo", Context.MODE_PRIVATE).edit().putString("userName", name).commit();
                          }
                      }
                  }


                break;
            case R.id.direct_login:
                finish();
                break;
        }
    }

    private void InstanceActivtiyApplication(){

        ActivityManager activityManager= ActivityManager.getInstance();
        activityManager.addActivity(this);
    }
}
