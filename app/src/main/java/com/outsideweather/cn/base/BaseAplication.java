package com.outsideweather.cn.base;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.common.BaiduMapSDKException;
import com.qweather.sdk.view.HeConfig;
import com.outsideweather.cn.util.HeFengApi;


/**
 * email：
 * description：初始化Aplication
 */
public class BaseAplication extends Application {
    private static BaseAplication baseAplication;

    public static BaseAplication getContext(){
        return baseAplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //提供以下两种方式进行初始化操作：
        //第一：默认初始化
        baseAplication=this;
        HeConfig.init( HeFengApi.HE_FENG_APP_ID,  HeFengApi.HE_FENG_APP_KEY);
        HeConfig.switchToDevService();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.setAgreePrivacy(this,true);
        try{
            // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext

            SDKInitializer.initialize(this);

        }catch (BaiduMapSDKException e) {
        }
    }
}
