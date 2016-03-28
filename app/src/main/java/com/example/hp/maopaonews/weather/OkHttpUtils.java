package com.example.hp.maopaonews.weather;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

/**
 * Created by hp on 2015/12/31.
 */
public class OkHttpUtils {

    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private OkHttpUtils() {
        mOkHttpClient = new OkHttpClient();
        //设置连接时间的长度与单位
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        /** //cookie enabled
       // CookieManager  是CookieHandler的一个子类 实现自动化管理 持久cookie

         * ACCEPT_ALL
         一种预定义策略，表示接受所有 cookie。
         ACCEPT_NONE
         一种预定义策略，表示不接受任何 cookie。
         ACCEPT_ORIGINAL_SERVER
         一种预定义策略，表示只接受来自原始服务器的 cookie。
         CookieStore 是处理HTTP缓存策略的有关的类（相当于一个缓存器 可读可写）
         */
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());//主线程的Handler

    }

    private synchronized static OkHttpUtils getmInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpUtils();
        }
        return mInstance;
    }

    /**
     * get请求
     *
     * @param url      请求url
     * @param callback 请求回调
     */
    public static void get(String url, ResultCallback callback) {

        getmInstance().getRequest(url,callback);
    }
  private void getRequest(String url, final  ResultCallback callback){
       //建立请求
      final Request request=new Request.Builder().url(url).build();
      deliveryResults(callback,request);

  }
private void deliveryResults(final  ResultCallback callback, final Request request){
    mOkHttpClient.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {
               sendFailCallback(callback,e);
            Log.d("BBB","onFailure");
        }

        @Override
        public void onResponse(Response response) throws IOException {

            Log.d("BBB","onResponse");
            try{
            String str=response.body().string();
                sendSuccessCallBack(callback, str);

            }catch ( final Exception e){
                sendFailCallback(callback, e);
            }
        }
    });

}
    private  void sendFailCallback(final  ResultCallback callback, final Exception e){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if(callback!=null){
                    callback.onFailure( e);

                }
            }
        });

    }

    private void sendSuccessCallBack(final  ResultCallback callback, final Object object){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if(callback!=null){
                    callback.onSuccess(object);
                }
            }
        });

    }
    public static abstract class ResultCallback<T> {
        //Type接口
        Type mType;


        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());

        }
           //获取模板参数的类型
        static Type getSuperclassTypeParameter(Class<?> subclass) {
             //获取该类的父类
            Type superclass = subclass.getGenericSuperclass();
            Log.d("HHH","subclass="+subclass.getGenericSuperclass());
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            //获得模板参数的类型
            ParameterizedType parameterized = (ParameterizedType) superclass;
               //返回一个序列化的类型
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);

        }


        /**
         * 请求成功回调
         * @param response
         */
        public abstract void onSuccess(T response);

        /**
         * 请求失败回调
         * @param e
         */
        public abstract void onFailure(Exception e);
    }
}