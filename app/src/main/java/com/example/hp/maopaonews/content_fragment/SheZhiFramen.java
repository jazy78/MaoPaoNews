package com.example.hp.maopaonews.content_fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp.maopaonews.Activities.LoginActivity;
import com.example.hp.maopaonews.Activities.MyWallet;
import com.example.hp.maopaonews.Activities.ReadHistoryActivity;
import com.example.hp.maopaonews.Activities.RegisterActivity;
import com.example.hp.maopaonews.Activities.Setting_glodmall;
import com.example.hp.maopaonews.Activities.Setting_headpage;
import com.example.hp.maopaonews.Activities.Setting_my_Task;
import com.example.hp.maopaonews.Activities.Setting_set_page;
import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.Setting_utils.SearchDB;
import com.example.hp.maopaonews.Setting_utils.TouXiangCache;
import com.umeng.socialize.utils.Log;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp on 2016/1/13.
 */
public class SheZhiFramen extends Fragment implements View.OnClickListener{

    private View view;
    //判断登录的标记
    private static boolean flag;
    private String user_name;
    private String pic_path;

    static CircleImageView picture;
    static TextView userName, jinbiCount;
    TextView setting, userlevel, read, messageText, goldMallText, myTaskText, myWalletText, mymailboxText;
    ImageView readNumber, collectNumber, gentieNumber;
    static ImageView goldNumber;
    RelativeLayout reader, collect, Thread, gold;
    LinearLayout myMessage, goldMall, myTask, myWallet, mymailbox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            if (view == null) {
                view = initView(inflater);
            }
            return view;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.hgz_activity_main_fragment, null, false);
        initSubView(view);
        user_name = SearchDB.createDb(getActivity(), "userName");
        if(user_name!=null){
            Log.d("JJJ","user_name="+user_name);
            Log.d("JJJ","wojinliale");
            userName.setText(user_name);
            userlevel.setText("跟帖局科员");
            flag=true;

            pic_path = SearchDB.TouXiangDb(getActivity(), "pic_path");
            if(pic_path!=null){
                Log.d("JJJ","我进来了");
                Bitmap bitmap= TouXiangCache.getphoto(pic_path);
                picture.setImageBitmap(bitmap);
            }

        }else {
            jinbiCount.setVisibility(View.GONE);

            goldNumber.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void initSubView(View view) {
        picture = (CircleImageView) view.findViewById(R.id.picture);
        setting = (TextView) view.findViewById(R.id.setting);
        userName = (TextView) view.findViewById(R.id.userName);
        userlevel = (TextView) view.findViewById(R.id.userlevel);
        goldNumber = (ImageView) view.findViewById(R.id.gold_number);
        jinbiCount = (TextView) view.findViewById(R.id.jinbiCount);
        reader = (RelativeLayout) view.findViewById(R.id.reader);
        goldMallText = (TextView) view.findViewById(R.id.goldMallText);
        goldMall = (LinearLayout) view.findViewById(R.id.goldMall);
        myTask = (LinearLayout) view.findViewById(R.id.myTask);
        myWallet = (LinearLayout) view.findViewById(R.id.myWallet);
        initOnClick();
    }


    private void initOnClick(){
        picture.setOnClickListener(this);
        setting.setOnClickListener(this);
        userName.setOnClickListener(this);
        reader.setOnClickListener(this);
        goldMallText.setOnClickListener(this);
        goldMall.setOnClickListener(this);
        myTask.setOnClickListener(this);
        myWallet.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.userName:
                if(flag){


                }else{

                    Intent intent2=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent2);
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


                }

            case R.id.picture:
                if(flag){

                    Intent intent1 = new Intent(getActivity(), Setting_headpage.class);
                    startActivity(intent1);
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }else {
                    Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent2);
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }
                break;
            case  R.id.setting:
                Intent intent=new Intent(getActivity(), Setting_set_page.class);
                startActivity(intent);
                break;
            case R.id.reader:

                startActivity(new Intent(getActivity(), ReadHistoryActivity.class));
                break;
            case R.id.goldMall:
                Intent intent1=new Intent(getActivity(), Setting_glodmall.class);
                startActivity(intent1);
                break;
            case R.id.myTask:
                     if(flag){
                         Intent intent2=new Intent(getActivity(), Setting_my_Task.class);
                         getActivity().overridePendingTransition(R.anim.push_left_in1,R.anim.push_left_out1);
                         startActivity(intent2);
                     }else {
                         Intent intent2=new Intent(getActivity(),LoginActivity.class);
                         getActivity().overridePendingTransition(R.anim.push_left_in1,R.anim.push_left_out1);
                         startActivity(intent2);

                     }
                 break;

            case R.id.myWallet:
                if(flag){
                      Intent intent2=new Intent(getActivity(), MyWallet.class);
                    startActivity(intent2);

                }else {
                    Intent intent2=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent2);


                }
                break;
        }
    }

   public static Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what=msg.what;
            switch (what){
                case 1:
                    Log.d("ZZZ","成功啦");
                    userName.setText("立即登录");
                    goldNumber.setVisibility(View.VISIBLE);
                    jinbiCount.setVisibility(View.GONE);
                    picture.setImageResource(R.mipmap.biz_tie_user_avater_default_common);
                    flag=false;
                    Log.d("ZZZ","flag="+flag);
                break;
                case 2:
                    Bitmap bp=(Bitmap)msg.obj;
                    if(bp!=null){
                        picture.setImageBitmap(bp);

                    }
            }

        }
    };

    @Override
    public void onStart() {
        super.onStart();
        Log.d("JJJ","onStart.....");
      Message message=new Message();
        message.what=1;
        handler1.sendMessage(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        Message message=new Message();
        message.what=1;
        handler1.sendMessage(message);
        Log.d("JJJ","onResume.....");
    }

    public  Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            user_name = SearchDB.createDb(getActivity(), "userName");
            if(user_name!=null){
                Log.d("JJJ","user_name="+user_name);
                Log.d("JJJ","wojinliale");
                userName.setText(user_name);
                userlevel.setText("跟帖局科员");
                flag=true;

                pic_path = SearchDB.TouXiangDb(getActivity(), "pic_path");
                if(pic_path!=null){
                    Log.d("JJJ","我进来了");
                    Bitmap bitmap= TouXiangCache.getphoto(pic_path);
                    picture.setImageBitmap(bitmap);
                }

            }else {
                jinbiCount.setVisibility(View.GONE);

                goldNumber.setVisibility(View.VISIBLE);
            }
        }
    };

    //////////////////
}
