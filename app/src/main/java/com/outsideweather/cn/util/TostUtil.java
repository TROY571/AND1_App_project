package com.outsideweather.cn.util;

import android.content.Context;
import android.widget.Toast;

import com.outsideweather.cn.base.BaseAplication;

/**
 * email：
 * description：
 */
public class TostUtil {
    public static void showTost(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
    public static void showTost(String msg){
        Toast.makeText(BaseAplication.getContext(),msg,Toast.LENGTH_LONG).show();
    }
}
