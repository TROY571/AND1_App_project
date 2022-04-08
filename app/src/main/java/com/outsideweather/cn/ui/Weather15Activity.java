package com.outsideweather.cn.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.view.QWeather;
import com.outsideweather.cn.R;
import com.outsideweather.cn.adpter.Weather15Adapter;
import com.outsideweather.cn.base.BaseActivity;
import com.outsideweather.cn.util.StatusBarUtil;
import com.outsideweather.cn.util.TostUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期：2022/4/2
 * email：
 * description：15天 天气预报
 */
public class Weather15Activity extends BaseActivity {
    public static  void startWeather15Activity(Context context,String city,String postionDate){
        Intent intent=new Intent(context,Weather15Activity.class);
        intent.putExtra("city",city);
        intent.putExtra("postionDate",postionDate);
        context.startActivity(intent);
    }
    private RelativeLayout rlTitle;
    private ImageView ivBack;
    private TextView ivTitle;
    private RecyclerView rv;
    private  String city;
    private String postionDate;
    List< WeatherDailyBean.DailyBean> dailyBeanList = new ArrayList<>();
    Weather15Adapter weather15Adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_15);
        StatusBarUtil.setLightStatusBar(this, false);
        city=getIntent().getStringExtra("city");
        postionDate=getIntent().getStringExtra("postionDate");
        Log.i("shinemao","city"+city);
        Log.i("shinemao","postionDate"+postionDate);
        initView();
        get15DayWeather(postionDate);
    }

    private void initView() {
        rlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivTitle = (TextView) findViewById(R.id.iv_title);
        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) rlTitle.getLayoutParams();
        lp2.topMargin = StatusBarUtil.getStatusBarHeight(this);
        rlTitle.setLayoutParams(lp2);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivTitle.setText(city);
        weather15Adapter = new Weather15Adapter(dailyBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(linearLayoutManager);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        rv.setOnFlingListener(null);//避免抛异常
        //滚动对齐，使RecyclerView像ViewPage一样，一次滑动一项,居中
        snapHelper.attachToRecyclerView(rv);
        rv.setAdapter(weather15Adapter);
    }

    //七天天气预报
    public void get15DayWeather(String lotion) {
        QWeather.getWeather15D(this, lotion, Lang.EN, Unit.METRIC, new QWeather.OnResultWeatherDailyListener() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(WeatherDailyBean weatherDailyBean) {
                if (weatherDailyBean.getCode() == Code.OK) {
                    Log.i("shinemao","shinemao"+weatherDailyBean.getCode());
                    dailyBeanList.clear();
                    dailyBeanList.addAll(weatherDailyBean.getDaily());
                    weather15Adapter.notifyDataSetChanged();
                } else if (weatherDailyBean.getCode() == Code.NO_DATA) {
                    TostUtil.showTost(Code.NO_DATA.getTxt());
                } else if (weatherDailyBean.getCode() == Code.NO_MORE_REQUESTS) {
                    TostUtil.showTost(Code.NO_MORE_REQUESTS.getTxt());
                } else if (weatherDailyBean.getCode() == Code.TOO_FAST) {
                    TostUtil.showTost(Code.TOO_FAST.getTxt());
                } else if (weatherDailyBean.getCode() == Code.PERMISSION_DENIED) {
                    TostUtil.showTost(Code.PERMISSION_DENIED.getTxt());
                } else {
                    TostUtil.showTost("请求错误,请重试！");
                }

            }
        });
    }
}
