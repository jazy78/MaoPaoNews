package com.example.hp.maopaonews.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.content_fragment.SettingsFragment;
import com.example.hp.maopaonews.content_fragment.SheZhiFramen;
import com.example.hp.maopaonews.utils.HttpPostThread;
import com.example.hp.maopaonews.utils.ThreadPoolUtils;
import com.example.hp.maopaonews.utils.Utils;
import com.umeng.socialize.controller.UMSocialService;

/**
 * Created by hp on 2016/1/13.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton back;
    private TextView login_password_back, register;
    private EditText login_zhanghao, login_password;
    private Button login_button;
    private LinearLayout weixin_login, qq_login, xinlang_login;

    UMSocialService mController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InstanceActivtiyApplication();
        initView();

    }

    private void initView() {
        back = (ImageButton) findViewById(R.id.loginimage_back);
        back.setOnClickListener(this);
        login_password_back = (TextView) findViewById(R.id.login_password_back);//找回密码
        login_password_back.setOnClickListener(this);
        register = (TextView) findViewById(R.id.register);//注册
        register.setOnClickListener(this);
        login_zhanghao = (EditText) findViewById(R.id.login_zhanghao);//账号
        login_password = (EditText) findViewById(R.id.login_password);//密码

        weixin_login = (LinearLayout) findViewById(R.id.weixin_login);//微信登陆
        weixin_login.setOnClickListener(this);
        qq_login = (LinearLayout) findViewById(R.id.qq_login);//QQ登录
        qq_login.setOnClickListener(this);
        xinlang_login = (LinearLayout) findViewById(R.id.xinlang_login);//新浪登陆
        xinlang_login.setOnClickListener(this);
        login_button = (Button) findViewById(R.id.login_button);//登陆按钮
        login_button.setOnClickListener(this);

        login_zhanghao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1=login_password.getText().toString().trim();
                String s2=s.toString().trim();
                if(!"".equals(s1)&&!"".equals(s2)){
                    login_button.setEnabled(true);
                    Drawable drawable=getResources().getDrawable(R.drawable.login_button_a);
                    login_button.setBackground(drawable);
                }else {

                    login_button.setEnabled(false);
                    login_button.setBackground(getResources().getDrawable(R.drawable.biankuang));
                }

            }
        });

        login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String s1 = login_zhanghao.getText().toString().trim();
                String s2 = s.toString().trim();
                if (!"".equals(s1) && !"".equals(s2)) {
                    login_button.setEnabled(true);
                    Drawable drawable = getResources().getDrawable(R.drawable.login_button_a);
                    login_button.setBackground(drawable);
                } else {
                    login_button.setEnabled(false);
                    login_button.setBackground(getResources().getDrawable(R.drawable.biankuang));
                }

            }
        });


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.loginimage_back:
                finish();
                break;
           case  R.id.register:

               startActivity(new Intent(this,RegisterActivity.class));
                finish();
               overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
            break;
            case R.id.login_button:
                String zhanghao=login_zhanghao.getText().toString().trim();
                String password=login_password.getText().toString().trim();
                 if(Utils.IsPhoneNumber(zhanghao)){
                     login(zhanghao, password);

                 }else {
                     Toast.makeText(this, "输入账号错误...", Toast.LENGTH_SHORT).show();
                 }
                break;
        }
    }
    private void  login(String zhanghao,String password){

        ThreadPoolUtils.excute(new HttpPostThread(this,zhanghao,password,handler));

    }

    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==200){
                boolean b=(boolean)msg.obj;
                if(b){
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                   /* Message message=new Message();
                    message.what=1;
                    SettingsFragment.handler.sendMessage(message);
*/
                    SettingsFragment.setIsLoading(true);
                    finish();
                    //SheZhiFramen sheZhiFramen=new SheZhiFramen();
                  //  sheZhiFramen.initView(LayoutInflater.from(getApplication()));

                }else {
                    Toast.makeText(LoginActivity.this,"账号或密码不正确!请重新输入",Toast.LENGTH_SHORT).show();
                }

            }

        }
    };

    private void InstanceActivtiyApplication(){

        ActivityManager activityManager= ActivityManager.getInstance();
        activityManager.addActivity(this);
    }
    ////////////////
}
