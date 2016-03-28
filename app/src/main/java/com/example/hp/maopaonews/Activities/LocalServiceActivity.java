package com.example.hp.maopaonews.Activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.hp.maopaonews.CostomAdapter.RecycleAdapter;
import com.example.hp.maopaonews.R;
import com.umeng.socialize.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/1/19.
 */
public class LocalServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private ImageButton menu;
    private RecyclerView recyclerView;
    private List<Integer> drawableList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    RecycleAdapter adapter;
    RelativeLayout relativeLayout;
    WindowManager.LayoutParams lp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loacl_service);
        back = (ImageView) findViewById(R.id.errorback);
        menu = (ImageButton) findViewById(R.id.menu);
        relativeLayout = (RelativeLayout) findViewById(R.id.root_layout);
        lp =getWindow().getAttributes();
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new RecycleAdapter(this, drawableList, stringList);

        recyclerView.setAdapter(adapter);

        initOnClick();

    }

    private void initOnClick() {
        back.setOnClickListener(this);
        menu.setOnClickListener(this);

    }

    private void initData() {
        drawableList.add(R.drawable.work1);
        drawableList.add(R.drawable.shopping1);
        drawableList.add(R.drawable.nanny1);
        drawableList.add(R.drawable.uesedcar1);

        stringList.add("找工作");
        stringList.add("淘二手");
        stringList.add("找保姆");
        stringList.add("二手车");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.errorback:
                finish();
                break;
            case R.id.menu:
                Log.d("VVV", "运行了");
                initpopowindow();
                break;
            case R.id.cancal:
                popupWindow.dismiss();
                break;

        }
    }

    PopupWindow popupWindow;
    Button buttoncancal;

    private void initpopowindow() {
        popupWindow = new PopupWindow(getWindowManager().getDefaultDisplay().getWidth(),
                getWindowManager().getDefaultDisplay().getHeight() / 2);
        View view = View.inflate(this, R.layout.pop_menu, null);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        popupWindow.setAnimationStyle(R.style.popo_anim);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(menu, Gravity.BOTTOM, 0, 0);
        buttoncancal = (Button) view.findViewById(R.id.cancal);
        buttoncancal.setOnClickListener(this);
        lp.alpha = 0.5f;
        lp.dimAmount=0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new  PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss () {

                lp.alpha = 1f;
                lp.dimAmount=1f;
                getWindow().setAttributes(lp);

            }
        });
        popupWindow.setOutsideTouchable(true);

    }


}
