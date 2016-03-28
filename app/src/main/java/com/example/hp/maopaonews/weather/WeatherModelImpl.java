package com.example.hp.maopaonews.weather;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by hp on 2016/1/4.
 */
public class WeatherModelImpl implements WeatherModer {


    @Override
    public void loadWeatherData(String cityName, final LoadWeatherListener listener) {
        try {
            String  url=Urls.WEATHER+ URLEncoder.encode(cityName,"utf-8");
            Log.d("URL","url="+url);
            OkHttpUtils.ResultCallback<String> callback=new OkHttpUtils.ResultCallback<String>() {
                @Override
                public void onSuccess(String response) {
                    Log.d("Response","response="+response);
                    List<WeatherBean> lists=WeatherJsonUtils.getWeatherInfo(response);
                    listener.onSuccess(lists);
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onFailure("load weather data failure.", e);
                }
            };
            OkHttpUtils.get(url,callback);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }


    }

    @Override
    public void loadLocation(Context context, final LoadLocationListener listener) {
        /**
         * LocationManager获取位置信息的途径，
         * 常用的有两种：GPS和NETWORK。GPS定位更精确，缺点是只能在户外使用，耗电严重，
         * 并且返回用户位置信息的速度远不能满足用户需求。
         * NETWORK通过基站和Wi-Fi信号来获取位置信息，室内室外均可用，速度更快，耗电更少。
         * 为了获取用户位置信息，我们可以使用其中一个，也可以同时使用两个
         */
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
           //6.0以上判断是否有权限
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                listener.onFailure("location failure", null);
                return;
            }
        }
       //  List<String> lists=locationManager.getProviders(true);//获取可用的privider
         //获得最后一次provider 提供的位置信息
        //Location 对象中包含了维度，经度，海拔等一系列的位置信息
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null) {
            listener.onFailure("location failure", null);
            return;
        }
        double latitude = location.getLatitude();//经度
        double longitude = location.getLongitude();//维度

        String url = getLocationURL(latitude, longitude);
        Log.d("URL","URL="+url);


        OkHttpUtils.ResultCallback<String> callback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                //response 是根据维度经度获取的 位置信息的Json
                String city = WeatherJsonUtils.getCity(response);
                if (TextUtils.isEmpty(city)) {
                    listener.onFailure("load location info failure", null);
                } else {

                    listener.onSuccess(city);
                }
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load location info failure.", e);
            }
        };
        OkHttpUtils.get(url, callback);
    }

    private String getLocationURL(double latitude, double longitude) {

        StringBuffer sb = new StringBuffer(Urls.INTERFACE_LOCATION);
        sb.append("?output=json").append("&referer=32D45CBEEC107315C553AD1131915D366EEF79B4");
        sb.append("&location=").append(latitude).append(",").append(longitude);
        return sb.toString();
    }

    public interface LoadWeatherListener {
        void onSuccess(List<WeatherBean> list);

        void onFailure(String msg, Exception e);
    }

    public interface LoadLocationListener {
        void onSuccess(String cityName);

        void onFailure(String msg, Exception e);
    }
}
