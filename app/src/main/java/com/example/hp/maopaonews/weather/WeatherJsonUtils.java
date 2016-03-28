package com.example.hp.maopaonews.weather;

import android.text.TextUtils;


import com.example.hp.maopaonews.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/1/5.
 */
public class WeatherJsonUtils {


    /**
     * 从定位的json字串中获取城市
     *
     * @param json
     * @return
      {
    "status":"OK",
    "result":{
    "location":{
    "lng":120.339326,
    "lat":30.314524
    },
    "formatted_address":"浙江省杭州市江干区银沙路",
    "business":"下沙,白杨,高教园",
    "addressComponent":{
    "city":"杭州市",
    "direction":"",
    "distance":"",
    "district":"江干区",
    "province":"浙江省",
    "street":"银沙路",
    "street_number":""
    }
    "cityCode":179
   }
}
     */
    public static String getCity(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        //获取jsonObject的一个元素
        JsonElement status = jsonObject.get("status");
        //String status1=jsonObject.get("status").getAsString();
        if (status != null && "OK".equals(status.getAsString())) {
            JsonObject result = jsonObject.getAsJsonObject("result");
            if (result != null) {
                JsonObject addressCompoment = result.getAsJsonObject("addressComponent");
                if (addressCompoment != null) {
                    JsonElement cityElement = addressCompoment.get("city");
                    if (cityElement != null) {
                        return cityElement.getAsString().replace("市", "");
                    }
                }

            }

        }
        return null;
    }

    /**
     * 获取天气信息
     *
     * @param json
     * @return
     * {"desc":"OK",
     * "status":1000,
     * "data":{
     * "wendu":"8",
     * "ganmao":"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。",
     * "forecast":[
     * {"fengxiang":"北风","fengli":"3-4级","high":"高温 11℃","type":"中雨","low":"低温 7℃","date":"5日星期二"},
     * {"fengxiang":"东北风","fengli":"微风级","high":"高温 9℃","type":"小雨","low":"低温 5℃","date":"6日星期三"},
     * {"fengxiang":"东北风","fengli":"微风级","high":"高温 8℃","type":"多云","low":"低温 3℃","date":"7日星期四"},
     * {"fengxiang":"北风","fengli":"微风级","high":"高温 9℃","type":"阴","low":"低温 1℃","date":"8日星期五"},
     * {"fengxiang":"东北风","fengli":"微风级","high":"高温 10℃","type":"晴","low":"低温 3℃","date":"9日星期六"}
     * ],
     * "yesterday":{"fl":"微风","fx":"东北风","high":"高温 13℃","type":"小雨","low":"低温 9℃","date":"4日星期一"},
     * "aqi":"38","city":"杭州"}
     * }
     *
     *
     */
    public static List<WeatherBean> getWeatherInfo(String json) {
        List<WeatherBean> list = new ArrayList<>();
        if (TextUtils.isEmpty(json)) {
            return list;
        }
        //Json分析器 JsonParser
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        String status = jsonObject.get("status").getAsString();
        if ("1000".equals(status)) {
            JsonArray jsonArray = jsonObject.getAsJsonObject("data").getAsJsonArray("forecast");
            for (int i = 0; i < jsonArray.size(); i++) {
                WeatherBean weatherBean = getWeatherBeanFromJson(jsonArray.get(i).getAsJsonObject());
                list.add(weatherBean);
            }

        }
              return list;
    }
    private static WeatherBean getWeatherBeanFromJson(JsonObject jsonObject) {
        // WeatherBean weatherBean= JsonUtils.deserialize(jsonObject,WeatherBean.class);
        String temperature = jsonObject.get("high").getAsString() + " " + jsonObject.get("low").getAsString();
        String weather = jsonObject.get("type").getAsString();
        String wind = jsonObject.get("fengxiang").getAsString();
        String date = jsonObject.get("date").getAsString();
        WeatherBean weatherBean = new WeatherBean();

        weatherBean.setDate(date);
        weatherBean.setTemperature(temperature);
        weatherBean.setWeather(weather);
        weatherBean.setWeek(date.substring(date.length()-3));
        weatherBean.setWind(wind);
        weatherBean.setImageRes(getWeatherImage(weather));
            return  weatherBean;
    }
    public static int getWeatherImage(String weather) {
        if (weather.equals("多云") || weather.equals("多云转阴") || weather.equals("多云转晴")) {
            return R.drawable.biz_plugin_weather_duoyun;
        } else if (weather.equals("中雨") || weather.equals("中到大雨")) {
            return R.drawable.biz_plugin_weather_zhongyu;
        } else if (weather.equals("雷阵雨")) {
            return R.drawable.biz_plugin_weather_leizhenyu;
        } else if (weather.equals("阵雨") || weather.equals("阵雨转多云")) {
            return R.drawable.biz_plugin_weather_zhenyu;
        } else if (weather.equals("暴雪")) {
            return R.drawable.biz_plugin_weather_baoxue;
        } else if (weather.equals("暴雨")) {
            return R.drawable.biz_plugin_weather_baoyu;
        } else if (weather.equals("大暴雨")) {
            return R.drawable.biz_plugin_weather_dabaoyu;
        } else if (weather.equals("大雪")) {
            return R.drawable.biz_plugin_weather_daxue;
        } else if (weather.equals("大雨") || weather.equals("大雨转中雨")) {
            return R.drawable.biz_plugin_weather_dayu;
        } else if (weather.equals("雷阵雨冰雹")) {
            return R.drawable.biz_plugin_weather_leizhenyubingbao;
        } else if (weather.equals("晴")) {
            return R.drawable.biz_plugin_weather_qing;
        } else if (weather.equals("沙尘暴")) {
            return R.drawable.biz_plugin_weather_shachenbao;
        } else if (weather.equals("特大暴雨")) {
            return R.drawable.biz_plugin_weather_tedabaoyu;
        } else if (weather.equals("雾") || weather.equals("雾霾")) {
            return R.drawable.biz_plugin_weather_wu;
        } else if (weather.equals("小雪")) {
            return R.drawable.biz_plugin_weather_xiaoxue;
        } else if (weather.equals("小雨")) {
            return R.drawable.biz_plugin_weather_xiaoyu;
        } else if (weather.equals("阴")) {
            return R.drawable.biz_plugin_weather_yin;
        } else if (weather.equals("雨夹雪")) {
            return R.drawable.biz_plugin_weather_yujiaxue;
        } else if (weather.equals("阵雪")) {
            return R.drawable.biz_plugin_weather_zhenxue;
        } else if (weather.equals("中雪")) {
            return R.drawable.biz_plugin_weather_zhongxue;
        } else {
            return R.drawable.biz_plugin_weather_duoyun;
        }
    }
}
