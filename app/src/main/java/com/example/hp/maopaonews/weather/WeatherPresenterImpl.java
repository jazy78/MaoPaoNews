package com.example.hp.maopaonews.weather;

import android.content.Context;

import com.example.hp.maopaonews.utils.CommonUtil;


import java.util.List;

/**
 * Created by hp on 2016/1/4.
 */
public class WeatherPresenterImpl implements WeatherPresenter, WeatherModelImpl.LoadWeatherListener {

    private WeatherView mWeatherView;
    private WeatherModer mWeatherModel;//利用下载数据

    private Context mContext;

    public WeatherPresenterImpl(Context context, WeatherView weatherView) {
        this.mContext = context;
        this.mWeatherView = weatherView;
        mWeatherModel = new WeatherModelImpl();
    }

    @Override
    public void onSuccess(List<WeatherBean> list) {

        if(list!=null&&list.size()>0){
            WeatherBean todayWeather=list.remove(0);//移除第一个
            mWeatherView.setToday(todayWeather.getDate());
            mWeatherView.setTemperature(todayWeather.getTemperature());
            mWeatherView.setWeather(todayWeather.getWeather());
            mWeatherView.setWind(todayWeather.getWind());
            mWeatherView.setWeatherImage(todayWeather.getImageRes());

        }
        mWeatherView.setWeatherData(list);
        mWeatherView.hideProgress();
        mWeatherView.showWeatherLayout();
    }

    @Override
    public void onFailure(String msg, Exception e) {

        mWeatherView.hideProgress();
        mWeatherView.showErrorToast("获取天气数据失败");
    }

    @Override
           public void loadWeatherData() {
            mWeatherView.showProgress();
            if (!CommonUtil.isNetWork(mContext)) {
                mWeatherView.hideProgress();
                mWeatherView.showErrorToast("无网络连接");
                return;

        }
        WeatherModelImpl.LoadLocationListener listener = new WeatherModelImpl.LoadLocationListener() {
            @Override
            public void onSuccess(String cityName) {
                mWeatherView.setCity(cityName);
                mWeatherModel.loadWeatherData(cityName,WeatherPresenterImpl.this);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                    mWeatherView.showErrorToast("定位失败");
                    mWeatherView.setCity("深圳");
                    mWeatherModel.loadWeatherData("深圳",WeatherPresenterImpl.this);
            }
        };
              mWeatherModel.loadLocation(mContext,listener);

    }


    public static void test(){

    }
    //////////////
}
