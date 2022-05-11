package com.outsideweather.cn.adpter;

import android.widget.ImageView;

import androidx.annotation.Nullable;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.outsideweather.cn.R;
import com.outsideweather.cn.util.BaseDateUtils;
import com.outsideweather.cn.util.HeFengWeatherUtil;

import java.util.List;

public class Weather7DayAdapter extends BaseQuickAdapter< WeatherDailyBean.DailyBean, BaseViewHolder> {
    public Weather7DayAdapter(@Nullable List< WeatherDailyBean.DailyBean> data) {
        super(R.layout.item_7_day, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeatherDailyBean.DailyBean item) {
        helper.setText(R.id.tv_date,  BaseDateUtils.Week(item.getFxDate()))
                //max
                .setText(R.id.tv_temp_high, item.getTempMax() + "℃")
                //min
                .setText(R.id.tv_temp_low, " / " + item.getTempMin() + "℃");


        //background image
        ImageView weatherStateIcon = helper.getView(R.id.iv_weather_state);
        //weather code
        int code = Integer.parseInt(item.getIconDay());
        HeFengWeatherUtil.changeIcon(weatherStateIcon, code);
        helper.addOnClickListener(R.id.item_forecast);
    }
}
