package com.example.hp.maopaonews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.hp.maopaonews.CostomProgressDialog.CustomProgressDialog;
import com.example.hp.maopaonews.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by hp on 2016/1/6.
 */
public class XutilsGetData {

    private String CONFIG="config";

    private HttpUtils http;
    private CallBackHttp callBackHttp;
    private String data=null;
    private HttpHandler<String> hand;
    private static  CustomProgressDialog dialog;




    public void xUtilsHttp(final Context context, final  String url, final CallBackHttp callback, final boolean isprogressdialog){
        http=new HttpUtils();
        callBackHttp=callback;
        if(isprogressdialog){
                if(dialog==null){
                    dialog=new CustomProgressDialog(context,"正在加载中.......", R.anim.animation_frame);

                }
            dialog.show();
        }
        //打开子线程请求网络
        final CustomProgressDialog finalDialog = dialog;
        hand=http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                data=responseInfo.result;
                callback.handleData(data);
                saveData(context,url,data);
                if(finalDialog!=null){
                    finalDialog.dismiss();

                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

        }

        });

    }
    private SharedPreferences sp;
    public void saveData(Context context,String key,String data){
        if(sp==null){
            sp=context.getSharedPreferences(CONFIG,Context.MODE_PRIVATE);
            sp.edit().putString(key,data).commit();

        }

    }
    public  String getData(Context context,String key,String defValue){
        if(sp==null){

            sp=context.getSharedPreferences(CONFIG,Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);
    }

    private static BitmapUtils utils;
    public static  void xUtilsImageiv(ImageView iv, final String  imageurl, Context context, boolean isprogress){

          utils=new BitmapUtils(context,context.getFilesDir().getPath());
                  if(isprogress){
                      if(dialog==null) {

                          dialog = new CustomProgressDialog(context, "正在加载中......", R.anim.animation_frame);
                      }
                      dialog.show();
                  }
              utils.display(iv, imageurl, new BitmapLoadCallBack<ImageView>() {
                  @Override
                  public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                      imageView.setImageBitmap(bitmap);
                      if(dialog!=null){
                          dialog.dismiss();
                      }
                  }

                  @Override
                  public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {

                  }
              });
    }



    //数据接口回调
    public interface  CallBackHttp{
        void handleData(String data);
    }
    //图片的接口回调
    public interface CallBackImage{

        void handleData(Bitmap bitmap);
    }
}
