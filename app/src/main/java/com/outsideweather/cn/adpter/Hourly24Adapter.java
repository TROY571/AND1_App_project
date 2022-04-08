package com.outsideweather.cn.adpter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.outsideweather.cn.R;
import com.outsideweather.cn.util.DateUtils;
import com.outsideweather.cn.util.WeatherUtil;

import java.util.List;

/**
 * 逐小时预报数据列表适配器
 *
 *
 */
public class Hourly24Adapter extends BaseQuickAdapter<WeatherHourlyBean.HourlyBean, BaseViewHolder> {
    public Hourly24Adapter( @Nullable List<WeatherHourlyBean.HourlyBean> data) {
        super(R.layout.iten_24_hourly, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeatherHourlyBean.HourlyBean item) {
        String time = DateUtils.updateTime(item.getFxTime());
        //时间
        helper.setText(R.id.tv_time, WeatherUtil.showTimeInfo(time) + time)
                //温度
                .setText(R.id.tv_temperature, item.getTemp() + "℃");

        //天气状态图片
        ImageView weatherStateIcon = helper.getView(R.id.iv_weather_state);
        //获取天气状态码，根据状态码来显示图标
        int code = Integer.parseInt(item.getIcon());
        WeatherUtil.changeIcon(weatherStateIcon, code);
        helper.addOnClickListener(R.id.item_hourly);
    }
}
