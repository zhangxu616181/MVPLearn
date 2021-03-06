package com.bingo.king.app.umeng;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;

import com.bingo.king.R;
import com.bingo.king.mvp.model.entity.InitActBean;
import com.bingo.king.mvp.model.entity.dao.InitActBeanDao;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.BaseMediaObject;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

import java.util.ArrayList;

/**
 * <友盟分享管理类>
 */

public class UmengSocialManager {

    public static void init(Application app) {
        UMShareConfig config = new UMShareConfig();
        config.isOpenShareEditActivity(true);
        config.setSinaAuthType(UMShareConfig.AUTH_TYPE_SSO); //如果有安装客户端优先客户端授权登录
        config.isNeedAuthOnGetUserInfo(true); //每次登录都重新授权
        UMShareAPI.get(app).setShareConfig(config);


        String wxAppKey = app.getResources().getString(R.string.wx_app_id);
        String wxAppSecret = app.getResources().getString(R.string.wx_app_secret);

        String qqAppKey = app.getResources().getString(R.string.qq_app_id);
        String qqAppSecret = app.getResources().getString(R.string.qq_app_key);

        String sinaAppKey = app.getResources().getString(R.string.sina_app_key);
        String sinaAppSecret = app.getResources().getString(R.string.sina_app_secret);

        InitActBean model = InitActBeanDao.query();
        if (model != null) {
            if (!TextUtils.isEmpty(model.getWx_app_key()) && !TextUtils.isEmpty(model.getWx_app_secret())) {
                wxAppKey = model.getWx_app_key();
                wxAppSecret = model.getWx_app_secret();
            }
            if (!TextUtils.isEmpty(model.getQq_app_key()) && !TextUtils.isEmpty(model.getQq_app_secret())) {
                qqAppKey = model.getQq_app_key();
                qqAppSecret = model.getQq_app_secret();

            }
            if (!TextUtils.isEmpty(model.getSina_app_key()) && !TextUtils.isEmpty(model.getSina_app_secret())) {
                sinaAppKey = model.getSina_app_key();
                sinaAppSecret = model.getSina_app_secret();
            }
        }

        PlatformConfig.setWeixin(wxAppKey, wxAppSecret);
        PlatformConfig.setQQZone(qqAppKey, qqAppSecret);
        PlatformConfig.setSinaWeibo(sinaAppKey, sinaAppSecret, "http://sns.whalecloud.com");
    }

    public static SHARE_MEDIA[] getPlatform() {
        ArrayList<SHARE_MEDIA> list = new ArrayList<>();

//            if (isWeixinEnable())
//            {
        list.add(SHARE_MEDIA.WEIXIN);
        list.add(SHARE_MEDIA.WEIXIN_CIRCLE);
//            }

//            if (isQQEnable())
//            {
        list.add(SHARE_MEDIA.QQ);
        list.add(SHARE_MEDIA.QZONE);
//            }
//
//            if (isSinaEnable())
//            {
        list.add(SHARE_MEDIA.SINA);
//            }

        SHARE_MEDIA[] displaylist = new SHARE_MEDIA[list.size()];
        list.toArray(displaylist);
        return displaylist;
    }

    public static void shareWeixin(String title,
                                   String content,
                                   String imageUrl,
                                   String clickUrl,
                                   Activity activity,
                                   UMShareListener listener) {
//            if (!isWeixinEnable())
//            {
//                return;
//            }
        shareAction(title, content, imageUrl, clickUrl, activity, listener).setPlatform(SHARE_MEDIA.WEIXIN).share();
    }

    public static void shareWeixinCircle(String title,
                                         String content,
                                         String imageUrl,
                                         String clickUrl,
                                         Activity activity,
                                         UMShareListener listener) {
//            if (!isWeixinEnable())
//            {
//                return;
//            }
        shareAction(title, content, imageUrl, clickUrl, activity, listener).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).share();
    }

    public static void shareQQ(String title,
                               String content,
                               String imageUrl,
                               String clickUrl,
                               Activity activity,
                               UMShareListener listener) {
//            if (!isQQEnable())
//            {
//                return;
//            }
        shareAction(title, content, imageUrl, clickUrl, activity, listener).setPlatform(SHARE_MEDIA.QQ).share();
    }

    public static void shareQzone(String title,
                                  String content,
                                  String imageUrl,
                                  String clickUrl,
                                  Activity activity,
                                  UMShareListener listener) {
//            if (!isQQEnable())
//            {
//                return;
//            }
        shareAction(title, content, imageUrl, clickUrl, activity, listener).setPlatform(SHARE_MEDIA.QZONE).share();
    }

    public static void shareSina(String title,
                                 String content,
                                 String imageUrl,
                                 String clickUrl,
                                 Activity activity,
                                 UMShareListener listener) {
//            if (!isSinaEnable())
//            {
//                return;
//            }
        shareAction(title, content, imageUrl, clickUrl, activity, listener).setPlatform(SHARE_MEDIA.SINA).share();
    }


    private static ShareAction shareAction(String title,
                                           String content,
                                           String imageUrl,
                                           String clickUrl,
                                           Activity activity,
                                           UMShareListener listener) {
        Object media = null;
        if (!TextUtils.isEmpty(imageUrl)) {
            UMWeb web = new UMWeb(clickUrl);
            media = web;
        }
        return shareAction(title, content, media, imageUrl, activity, listener);
    }

    private static ShareAction shareAction(String title,
                                           String content,
                                           Object media,
                                           String imageUrl,
                                           Activity activity,
                                           UMShareListener listener) {
        ShareAction share = new ShareAction(activity);

        if (TextUtils.isEmpty(title)) {
            title = "title";
        }
        if (TextUtils.isEmpty(content)) {
            content = "content";
        }
        if (listener == null) {
            listener = defaultShareListener;
        }

        share.setDisplayList(getPlatform()).withText(content).setCallback(listener);

        if (media instanceof BaseMediaObject) {
            BaseMediaObject base = (BaseMediaObject) media;
            base.setTitle(title);
            base.setDescription(content);
            base.setThumb(new UMImage(activity, imageUrl));

            if (media instanceof UMImage) {
                UMImage image = (UMImage) media;
                share.withMedia(image);
            } else if (media instanceof UMusic) {
                UMusic music = (UMusic) media;
                share.withMedia(music);
            } else if (media instanceof UMVideo) {
                UMVideo video = (UMVideo) media;
                share.withMedia(video);
            } else if (media instanceof UMWeb) {
                UMWeb web = (UMWeb) media;
                share.withMedia(web);
            }
        }

        return share;
    }

    private static UMShareListener defaultShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };
}
