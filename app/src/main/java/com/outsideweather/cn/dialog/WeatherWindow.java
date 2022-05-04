package com.outsideweather.cn.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.outsideweather.cn.R;
import com.outsideweather.cn.activity.WeatherCityActivity;

/**
 * 日期：2022/4/2
 * email：
 * description：
 */
public class WeatherWindow {
    //右上角的弹窗
    private PopupWindow mPopupWindow;
    private Context context;

    public WeatherWindow(Context context){
        //初始化弹窗
        mPopupWindow = new PopupWindow(context);
        this.context=context;
        init();
    }

    public void init(){
        // 设置布局文件
        mPopupWindow.setContentView(LayoutInflater.from(context).inflate(R.layout.window_add, null));// 为了避免部分机型不显示，我们需要重新设置一下宽高
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));// 设置pop透明效果
        mPopupWindow.setAnimationStyle(R.style.pop_add);// 设置pop出入动画
        mPopupWindow.setFocusable(true);// 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        mPopupWindow.setTouchable(true);// 设置pop可点击，为false点击事件无效，默认为true
        mPopupWindow.setOutsideTouchable(true);// 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        // 设置pop关闭监听，用于改变背景透明度
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {//关闭弹窗
            @Override
            public void onDismiss() {
               mPopupWindow.dismiss();
            }
        });
        //绑定布局中的控件
        TextView changeCity = mPopupWindow.getContentView().findViewById(R.id.tv_change_city);
        changeCity.setOnClickListener(view -> {//切换城市
            mPopupWindow.dismiss();
            WeatherCityActivity.startMyCityActivity(context);
        });

    }
    public void show(View view){
        mPopupWindow.showAsDropDown(view, -100, 0);// 相对于 + 号正下面，同时可以设置偏移量
    }

}
