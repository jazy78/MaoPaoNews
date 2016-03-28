package com.example.hp.maopaonews.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.weather.WeatherBean;
import com.example.hp.maopaonews.weather.WeatherPresenter;
import com.example.hp.maopaonews.weather.WeatherPresenterImpl;
import com.example.hp.maopaonews.weather.WeatherView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/1/7.
 */
public class WeatherActivity extends AppCompatActivity implements WeatherView {
    private TextView mTodayTV;
    private ImageView mTodayWeatherImage;
    private TextView mTodayTemperatureTV;
    private TextView mTodayWindTV;
    private TextView mTodayWeatherTV;
    private TextView mCityTV;
    private ProgressBar mProgressBar;
    private LinearLayout mWeatherLayout;
    private LinearLayout mWeatherContentLayout;
    private WeatherPresenter presenter;
    ImageButton Back;
    ImageButton Loacation;
    ImageButton Share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_weather);
        InstanceActivtiyApplication();
        inittoobarbtn();
        presenter = new WeatherPresenterImpl(this, this);
        mTodayTV = (TextView) findViewById(R.id.today);
        mTodayWeatherImage = (ImageView) findViewById(R.id.weatherImage);
        mTodayTemperatureTV = (TextView) findViewById(R.id.weatherTemp);
        mTodayWindTV = (TextView) findViewById(R.id.wind);
        mTodayWeatherTV = (TextView) findViewById(R.id.weather);
        mCityTV = (TextView) findViewById(R.id.city);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mWeatherLayout = (LinearLayout) findViewById(R.id.weather_layout);
        mWeatherContentLayout = (LinearLayout) findViewById(R.id.weather_content);
        presenter.loadWeatherData();
    }

    private void InstanceActivtiyApplication() {

        ActivityManager activityManager = ActivityManager.getInstance();
        activityManager.addActivity(this);
    }

    private void inittoobarbtn() {
        Back = (ImageButton) findViewById(R.id.weather_back);
        Loacation = (ImageButton) findViewById(R.id.weather_location);
        Share = (ImageButton) findViewById(R.id.weather_share);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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
        List<View> adapterList = new ArrayList<>();
        for (WeatherBean weatherBean : lists) {

            View view = LayoutInflater.from(this).inflate(R.layout.item_weather, null, false);
            TextView dateTV = (TextView) view.findViewById(R.id.date);
            ImageView todayWeatherImage = (ImageView) view.findViewById(R.id.weatherImage);
            TextView todayTemperatureTV = (TextView) view.findViewById(R.id.weatherTemp);
            TextView todayWindTV = (TextView) view.findViewById(R.id.wind);
            TextView todayWeatherTV = (TextView) view.findViewById(R.id.weather);

            dateTV.setText(weatherBean.getWeek());
            todayTemperatureTV.setText(weatherBean.getTemperature());
            todayWindTV.setText(weatherBean.getWind());
            todayWeatherTV.setText(weatherBean.getWeather());
            todayWeatherImage.setImageResource(weatherBean.getImageRes());
            mWeatherContentLayout.addView(view);
            adapterList.add(view);
        }

    }

    @Override
    public void showErrorToast(String msg) {

    }
}
