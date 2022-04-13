package com.outsideweather.cn.event;

/**
 * 日期：2022/4/6
 * email：
 * description：
 */
public class WeatherCityEvent {
    public int code;
    public String name;
    public String searchKey;
    public WeatherCityEvent(int code, String name, String searchKey){
        this.code=code;
        this.name=name;
        this.searchKey=searchKey;
    }
}
