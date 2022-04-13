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
    protected void convert(BaseViewHolder helper, WeatherHourlyBean.HourlyBean item) {
        String time = BaseDateUtils.updateTime(item.getFxTime());
        //时间
        helper.setText(R.id.tv_time, HeFengWeatherUtil.showTimeInfo(time) + time)
                //温度
                .setText(R.id.tv_temperature, item.getTemp() + "℃");

        //天气状态图片
        ImageView weatherStateIcon = helper.getView(R.id.iv_weather_state);
        //获取天气状态码，根据状态码来显示图标
        int code = Integer.parseInt(item.getIcon());
        HeFengWeatherUtil.changeIcon(weatherStateIcon, code);
        helper.addOnClickListener(R.id.item_hourly);
    }
}
