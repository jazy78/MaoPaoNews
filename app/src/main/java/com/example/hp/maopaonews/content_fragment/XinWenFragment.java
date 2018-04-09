package com.example.hp.maopaonews.content_fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp.maopaonews.Activities.SearchActivity;
import com.example.hp.maopaonews.Activities.WeatherActivity;
import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.weather.WeatherBean;
import com.example.hp.maopaonews.weather.WeatherPresenter;
import com.example.hp.maopaonews.weather.WeatherPresenterImpl;
import com.example.hp.maopaonews.weather.WeatherView;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/1/6.
 */
public class XinWenFragment extends Fragment implements View.OnClickListener, WeatherView {

    private View contentView;
    private ImageButton btn_right;
    private View xuanfu_view;
    private List<Fragment> xinwen_framentlist;
    private WeatherPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initdata();
        super.onCreate(savedInstanceState);
        presenter = new WeatherPresenterImpl(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = initView(inflater);
        }

       // Log.d("BBB", "创建了View");
        return contentView;
    }

    private View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.xinwen_frament, null, false);
        xuanfu_view = View.inflate(getActivity(), R.layout.popwindow, null);
        initXuanfu_view(xuanfu_view);


        btn_right = (ImageButton) view.findViewById(R.id.btn_right);
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inintPopWindowView(v);
            }
        });

        final RadioGroup xinwen_Rradio = (RadioGroup) view.findViewById(R.id.xinwen_radiogroup);
        final ViewPager xinwen_viewpage = (ViewPager) view.findViewById(R.id.xinwen_viewpager);
        final HorizontalScrollView xinwen_scrollView = (HorizontalScrollView) view.findViewById(R.id.xinwen_scroll);
        final TextView xinwen_indicator = (TextView) view.findViewById(R.id.xinwen_indicator);

        XinWenFragmentAdapter fragmentAdapter = new XinWenFragmentAdapter(getChildFragmentManager());
        xinwen_viewpage.setAdapter(fragmentAdapter);
        xinwen_viewpage.setOffscreenPageLimit(2);

        //设置选中放大效果
        xinwen_Rradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //选中的RadioButton播放动画
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(500);
                sAnim.setFillAfter(true);
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(i);
                    if (radioButton.isChecked()) {
                        radioButton.startAnimation(sAnim);
                    } else {
                        radioButton.clearAnimation();
                    }
                }
                switch (checkedId) {
                    case R.id.xinwen_rb1:
                        xinwen_viewpage.setCurrentItem(0);
                        break;
                    case R.id.xinwen_rb2:
                        xinwen_viewpage.setCurrentItem(1);
                        break;
                    case R.id.xinwen_rb3:
                        xinwen_viewpage.setCurrentItem(2);
                        break;
                    case R.id.xinwen_rb4:
                        xinwen_viewpage.setCurrentItem(3);
                        break;
                    case R.id.xinwen_rb5:
                        xinwen_viewpage.setCurrentItem(4);
                        break;
                    case R.id.xinwen_rb6:
                        xinwen_viewpage.setCurrentItem(5);
                        break;
                    case R.id.xinwen_rb7:
                        xinwen_viewpage.setCurrentItem(6);
                        break;
                    case R.id.xinwen_rb8:
                        xinwen_viewpage.setCurrentItem(7);
                        break;
                    case R.id.xinwen_rb9:
                        xinwen_viewpage.setCurrentItem(8);
                        break;
                    case R.id.xinwen_rb10:
                        xinwen_viewpage.setCurrentItem(9);
                        break;
                }
            }
        });

        xinwen_viewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) xinwen_indicator
                        .getLayoutParams();
                params.leftMargin = (int) ((position + positionOffset) * params.width);
                xinwen_indicator.setLayoutParams(params);//设置了滑动
                Log.d("DDD", "我也调用");
            }

            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton = (RadioButton) xinwen_Rradio.getChildAt(position);
                radioButton.setChecked(true);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int radioBtnPiexls = radioButton.getWidth();

                int distance = (int) ((position + 0.5) * radioBtnPiexls) - displayMetrics.widthPixels / 2;
                Log.d("CCC", "displayMetrics=" + displayMetrics.widthPixels);
                Log.d("CCC", "distance=" + distance);
                //最多只能滑到边界（HorizationScrollView 不会滑出屏幕的边界，大于零向左平移）
                xinwen_scrollView.scrollTo(distance, 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    private TextView mTodayTV;
    private ImageView mTodayWeatherImage;
    private TextView mTodayTemperatureTV;
    private TextView mTodayWindTV;
    private TextView mTodayWeatherTV;
    private TextView mCityTV;
    private ProgressBar mProgressBar;
    private RelativeLayout mWeatherLayout;
    private LinearLayout mWeatherContentLayout;

    public void initXuanfu_view(View view) {
        mTodayTV = (TextView) view.findViewById(R.id.today);
        mTodayWeatherImage = (ImageView) view.findViewById(R.id.weatherImage);
        mTodayTemperatureTV = (TextView) view.findViewById(R.id.weatherTemp);
        mTodayWindTV = (TextView) view.findViewById(R.id.wind);
        mTodayWeatherTV = (TextView) view.findViewById(R.id.weather);
        mCityTV = (TextView) view.findViewById(R.id.city);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        mWeatherLayout = (RelativeLayout) view.findViewById(R.id.weather_layout);
        mWeatherContentLayout = (LinearLayout) view.findViewById(R.id.weather_content);
        presenter.loadWeatherData();
        mWeatherLayout.setOnClickListener(this);

    }

    PopupWindow popupWindow;


    private LinearLayout search;
    private LinearLayout shangtoutiao;
    private LinearLayout lixian;
    private LinearLayout yejian;
    private LinearLayout code;
    private LinearLayout friends;
    private TextView tv_lixian;
    private TextView tv_yejian;

    public void inintPopWindowView(View view) {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        popupWindow = new PopupWindow(width, height - 150);
        popupWindow.setAnimationStyle(R.style.AnimationFade);
        popupWindow.setContentView(xuanfu_view);//设置具体的窗口View
        //点击空白区关闭窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.showAsDropDown(view, 0, 0);
        search = (LinearLayout) xuanfu_view.findViewById(R.id.search);
        shangtoutiao = (LinearLayout) xuanfu_view.findViewById(R.id.shangtoutiao);
        lixian = (LinearLayout) xuanfu_view.findViewById(R.id.lixian);
        yejian = (LinearLayout) xuanfu_view.findViewById(R.id.yejian);
        code = (LinearLayout) xuanfu_view.findViewById(R.id.code);
        friends = (LinearLayout) xuanfu_view.findViewById(R.id.friends);
        tv_lixian = (TextView) xuanfu_view.findViewById(R.id.tv_lixian);
        tv_yejian = (TextView) xuanfu_view.findViewById(R.id.tv_yejian);
        search.setOnClickListener(this);

    }

    public void initdata() {

        xinwen_framentlist = new ArrayList<>();
        TouTiaoFrament toutiao = new TouTiaoFrament();
        Bundle bundletoutiao = new Bundle();
        bundletoutiao.putString("xinwendaohang", "头条");
        toutiao.setArguments(bundletoutiao);
        xinwen_framentlist.add(toutiao);

        TouTiaoFrament yule = new TouTiaoFrament();
        Bundle bundleyule = new Bundle();
        bundleyule.putString("xinwendaohang", "娱乐");
        yule.setArguments(bundleyule);
        xinwen_framentlist.add(yule);

        TouTiaoFrament tiyu = new TouTiaoFrament();
        Bundle bundletiyu = new Bundle();
        bundletiyu.putString("xinwendaohang", "体育");
        tiyu.setArguments(bundletiyu);
        xinwen_framentlist.add(tiyu);

        TouTiaoFrament caijing = new TouTiaoFrament();
        Bundle bundlecaijing = new Bundle();
        bundlecaijing.putString("xinwendaohang", "财经");
        caijing.setArguments(bundlecaijing);
        xinwen_framentlist.add(caijing);


        TouTiaoFrament keji = new TouTiaoFrament();
        Bundle bundlekeji = new Bundle();
        bundlekeji.putString("xinwendaohang", "科技");
        keji.setArguments(bundlekeji);
        xinwen_framentlist.add(keji);

        TouTiaoFrament shishang = new TouTiaoFrament();
        Bundle bundleshishang = new Bundle();
        bundleshishang.putString("xinwendaohang", "时尚");
        shishang.setArguments(bundleshishang);
        xinwen_framentlist.add(shishang);

        TouTiaoFrament lishi = new TouTiaoFrament();
        Bundle bundlelishi = new Bundle();
        bundlelishi.putString("xinwendaohang", "历史");
        lishi.setArguments(bundlelishi);
        xinwen_framentlist.add(lishi);

        TouTiaoFrament caipiao = new TouTiaoFrament();
        Bundle bundlecaipiao = new Bundle();
        bundlecaipiao.putString("xinwendaohang", "彩票");
        caipiao.setArguments(bundlecaipiao);
        xinwen_framentlist.add(caipiao);

        TouTiaoFrament junshi = new TouTiaoFrament();
        Bundle bundlejunshi = new Bundle();
        bundlejunshi.putString("xinwendaohang", "军事");
        junshi.setArguments(bundlejunshi);
        xinwen_framentlist.add(junshi);

        TouTiaoFrament youxi = new TouTiaoFrament();
        Bundle bundleyouxi = new Bundle();
        bundleyouxi.putString("xinwendaohang", "游戏");
        youxi.setArguments(bundleyouxi);
        xinwen_framentlist.add(youxi);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.weather_layout:
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                startActivity(intent);
                break;
            case R.id.search:
                popupWindow.dismiss();
                startActivity(new Intent(getActivity(), SearchActivity.class));
                getActivity().overridePendingTransition(R.anim.search_fade_in,R.anim.search_fade_out);
                break;
            case R.id.shangtoutiao:
                break;
            case R.id.lixian:
                break;
            case R.id.yejian:
                break;
            case R.id.code:
                break;
            case R.id.friends:
                break;
     }

    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWeatherLayout() {
        mWeatherLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCity(String city) {
        mCityTV.setText(city);
    }

    @Override
    public void setToday(String data) {
        mTodayTV.setText(data);
    }

    @Override
    public void setTemperature(String temperature) {
        mTodayTemperatureTV.setText(temperature);
    }

    @Override
    public void setWind(String wind) {
        mTodayWindTV.setText(wind);
    }

    @Override
    public void setWeather(String weather) {
        mTodayWeatherTV.setText(weather);
    }

    @Override
    public void setWeatherImage(int res) {
        mTodayWeatherImage.setImageResource(res);
    }

    @Override
    public void setWeatherData(List<WeatherBean> lists) {
        //
    }

    @Override
    public void showErrorToast(String msg) {
      //  Snackbar.make(getActivity().findViewById(R.id.xinwen_toutiao_lv), msg, Snackbar.LENGTH_SHORT).show();
    }

    class XinWenFragmentAdapter extends FragmentPagerAdapter {


        public XinWenFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return xinwen_framentlist.get(position);
        }

        @Override
        public int getCount() {
            return xinwen_framentlist == null ? 0 : xinwen_framentlist.size();
        }
    }


}
