package com.example.hp.maopaonews.weather;

import android.content.Context;

/**
 * Created by hp on 2016/1/4.
 */
public interface WeatherModer {
    void loadWeatherData(String cityName, WeatherModelImpl.LoadWeatherListener listener);

    void loadLocation(Context context, WeatherModelImpl.LoadLocationListener listener);
}
