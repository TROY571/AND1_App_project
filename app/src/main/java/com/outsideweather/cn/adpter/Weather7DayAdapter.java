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

/**
 *天气预报数据列表适配器
 *
 *
 *
 *
 *
 *
 *
 */
public class Weather7DayAdapter extends BaseQuickAdapter< WeatherDailyBean.DailyBean, BaseViewHolder> {
    public Weather7DayAdapter(@Nullable List< WeatherDailyBean.DailyBean> data) {
        super(R.layout.item_7_day, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeatherDailyBean.DailyBean item) {
        //日期DateUtils.dateSplitPlus(item.getFxDate())
        helper.setText(R.id.tv_date,  BaseDateUtils.Week(item.getFxDate()))
                //最高温
                .setText(R.id.tv_temp_height, item.getTempMax() + "℃")
                //最低温
                .setText(R.id.tv_temp_low, " / " + item.getTempMin() + "℃");


        //天气状态图片
        ImageView weatherStateIcon = helper.getView(R.id.iv_weather_state);
        //获取天气状态码，根据状态码来显示图标
        int code = Integer.parseInt(item.getIconDay());
        //调用工具类中写好的方法
        HeFengWeatherUtil.changeIcon(weatherStateIcon, code);
        //绑定点击事件的id
        helper.addOnClickListener(R.id.item_forecast);
    }
}
