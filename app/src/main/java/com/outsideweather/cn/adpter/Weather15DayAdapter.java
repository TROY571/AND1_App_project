package com.outsideweather.cn.adpter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.outsideweather.cn.base.BaseAplication;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.outsideweather.cn.R;
import com.outsideweather.cn.util.BaseDateUtils;
import com.outsideweather.cn.util.HeFengWeatherUtil;


import java.util.List;

/**
 * 更多天气预报信息数据适配器
 *
 *
 */
public class Weather15DayAdapter extends BaseQuickAdapter< WeatherDailyBean.DailyBean, BaseViewHolder> {
    public Weather15DayAdapter(@Nullable List< WeatherDailyBean.DailyBean> data) {
        super(R.layout.weather_item_15_day, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,  WeatherDailyBean.DailyBean item) {
        //最高温
        helper.setText(R.id.tv_temp_max, item.getTempMax() + "°")
                //最低温
                .setText(R.id.tv_temp_min, item.getTempMin() + "°")
                //日期描述
                .setText(R.id.tv_date_info, BaseDateUtils.Week(item.getFxDate()))
                //日期
                .setText(R.id.tv_date, BaseDateUtils.dateSplit(item.getFxDate()))
                //白天天气状况文字描述
                .setText(R.id.tv_weather_state_d, item.getTextDay())
                //晚间天气状况文字描述
                .setText(R.id.tv_weather_state_n, item.getTextNight())
                //白天风力信息
                .setText(R.id.tv_wind_360_d, item.getWind360Day() + "°")
                .setText(R.id.tv_wind_dir_d, item.getWindDirDay())
                .setText(R.id.tv_wind_scale_d, item.getWindScaleDay() + BaseAplication.getContext().getString(R.string.w_w15_9))
                .setText(R.id.tv_wind_speed_d, item.getWindSpeedDay() + "km/h")
                //晚上风力信息
                .setText(R.id.tv_wind_360_n, item.getWind360Night() + "°")
                .setText(R.id.tv_wind_dir_n, item.getWindDirNight())
                .setText(R.id.tv_wind_scale_n, item.getWindScaleNight() + BaseAplication.getContext().getString(R.string.w_w15_9))
                .setText(R.id.tv_wind_speed_n, item.getWindSpeedNight() + "km/h")
                //云量
                .setText(R.id.tv_cloud, item.getCloud() + "%")
                //紫外线
                .setText(R.id.tv_uvIndex, uvIndexToString(item.getUvIndex()))
                //能见度
                .setText(R.id.tv_vis, item.getVis() + "km")
                //降水量
                .setText(R.id.tv_precip, item.getPrecip() + "mm")
                //相对湿度
                .setText(R.id.tv_humidity, item.getHumidity() + "%")
                //大气压强
                .setText(R.id.tv_pressure, item.getPressure() + "hPa");


        //白天天气状态图片描述
        HeFengWeatherUtil.changeIcon(helper.getView(R.id.iv_weather_state_d), Integer.parseInt(item.getIconDay()));
        //晚上天气状态图片描述
        HeFengWeatherUtil.changeIcon(helper.getView(R.id.iv_weather_state_n), Integer.parseInt(item.getIconNight()));
    }

    private String uvIndexToString(String code) {//最弱(1)、弱(2)、中等(3)、强(4)、很强(5)
        String result = null;
        switch (code) {
            case "1":
                result = BaseAplication.getContext().getString(R.string.w_w15_10);
                break;
            case "2":
                result =  BaseAplication.getContext().getString(R.string.w_w15_11);
                break;
            case "3":
                result =  BaseAplication.getContext().getString(R.string.w_w15_12);
                break;
            case "4":
                result =  BaseAplication.getContext().getString(R.string.w_w15_13);
                break;
            case "5":
                result =  BaseAplication.getContext().getString(R.string.w_w15_14);
                break;
            default:
                result =  BaseAplication.getContext().getString(R.string.w_w15_15);
                break;
        }
        return result;
    }
}
