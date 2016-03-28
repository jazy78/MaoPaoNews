package com.example.hp.maopaonews.Setting_utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * Created by hp on 2016/1/11.
 */
public class ShareUtils {

    private static UMSocialService umSocialService = UMServiceFactory.getUMSocialService("com.example.hp.maopaonews");

    public static void shareContent(final Context context, String title, String url) {

        umSocialService.setShareContent(title);
        umSocialService.setShareImage(new UMImage(context, url));

        //分享给QQ好友
        UMQQSsoHandler handler3 = new UMQQSsoHandler((Activity) context, "1104966900", "voYmRxQSvtpCnGUE");
        handler3.addToSocialSDK();

        //分享到qq空间
        QZoneSsoHandler handler = new QZoneSsoHandler((Activity) context, "1105028083", "ok63VbnIL99OZKZS");
       // handler.setTargetUrl("http://f.hiphotos.baidu.com/image/pic/item/21a4462309f79052adae76d108f3d7ca7acbd5af.jpg");
        handler.addToSocialSDK();

        //添加微信平台
        UMWXHandler handler1 = new UMWXHandler(context, "wx7eb857bb5e706069", "c17f0d8ffd867124532c6293e759be89");
        handler1.addToSocialSDK();

        //添加微信朋友圈
        UMWXHandler handler2 = new UMWXHandler(context, "wx7eb857bb5e706069", "c17f0d8ffd867124532c6293e759be89");
        handler2.setToCircle(true);
        handler2.addToSocialSDK();

        //分享到腾讯微博
        umSocialService.getConfig().setSsoHandler(new TencentWBSsoHandler());

        RenrenSsoHandler handler4 = new RenrenSsoHandler(context, "481831", "038236ab416044b4881788902d956f25", "7acd8b4a55af4521a630753310ce50e5");
        umSocialService.getConfig().setSsoHandler(handler4);
        umSocialService.setAppWebSite(SHARE_MEDIA.RENREN, "http://www.baidu.com");
        //是否只有已经登录的用户才能打开分享选择页
        umSocialService.openShare((Activity) context, false);


    }

    public static void shareWeiXin(final Context context, String title, String url) {

        umSocialService.setShareContent(title);
        umSocialService.setShareImage(new UMImage(context, url));
        //添加微信平台
        UMWXHandler handler1 = new UMWXHandler(context, "wx7eb857bb5e706069", "c17f0d8ffd867124532c6293e759be89");
        handler1.addToSocialSDK();
        //添加微信朋友圈
        UMWXHandler handler2 = new UMWXHandler(context, "wx7eb857bb5e706069", "c17f0d8ffd867124532c6293e759be89");
        handler2.setToCircle(true);
        handler2.addToSocialSDK();
         SocializeListeners.SnsPostListener mSnsPostListener  = new SocializeListeners.SnsPostListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int stCode,
                                   SocializeEntity entity) {
                if (stCode == 200) {
                    Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(context,
                            "分享失败 : error code : " + stCode, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        };
        umSocialService.registerListener(mSnsPostListener);
        umSocialService.openShare((Activity) context, false);


    }

    public static void shareQQFriend(Context context, String title, String url) {
    Log.d("QQ", "我进来了");
        umSocialService.setShareContent(title);
        umSocialService.setShareImage(new UMImage(context, url));
        //分享给QQ好友
        UMQQSsoHandler handler3 = new UMQQSsoHandler((Activity) context, "1105028083", "ok63VbnIL99OZKZS");
        handler3.addToSocialSDK();
        umSocialService.openShare((Activity) context, false);
    }


    public static void shareQQZore(Context context, String title, String url) {
        umSocialService.setShareContent(title);
        umSocialService.setShareImage(new UMImage(context, url));
        QZoneSsoHandler handler = new QZoneSsoHandler((Activity) context, "1105028083", "ok63VbnIL99OZKZS");

        handler.setShareAfterAuthorize(false);
        handler.setTargetUrl("http://f.hiphotos.baidu.com/image/pic/item/21a4462309f79052adae76d108f3d7ca7acbd5af.jpg");
        umSocialService.openShare((Activity) context, false);
    }

}
