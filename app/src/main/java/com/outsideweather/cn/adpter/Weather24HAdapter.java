package com.outsideweather.cn.adpter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.outsideweather.cn.R;
import com.outsideweather.cn.util.BaseDateUtils;
import com.outsideweather.cn.util.HeFengWeatherUtil;

import java.util.List;

/**
 * 逐小时预报数据列表适配器
 *
 *
 */
public class Weather24HAdapter extends BaseQuickAdapter<WeatherHourlyBean.HourlyBean, BaseViewHolder> {

    public Weather24HAdapter(@Nullable List<WeatherHourlyBean.HourlyBean> data) {
        super(R.layout.item_24_hourly_weather, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, WeatherHourlyBean.HourlyBean item) {
        String time = BaseDateUtils.updateTime(item.getFxTime());
        //time
        viewHolder.setText(R.id.tv_time, HeFengWeatherUtil.showTimeInfo(time) + time)
                //temperature
                .setText(R.id.tv_temperature, item.getTemp() + "℃");

        //background image
        ImageView weatherStateIcon = viewHolder.getView(R.id.iv_weather_state);
        //weather code
        int code = Integer.parseInt(item.getIcon());
        HeFengWeatherUtil.changeIcon(weatherStateIcon, code);
        viewHolder.addOnClickListener(R.id.item_hourly);
    }
}
