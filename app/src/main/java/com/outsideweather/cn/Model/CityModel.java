package com.outsideweather.cn.Model;



/**
 * 日期：2022/4/2
 * email：
 * description：
 */
public class CityModel  {
    private String cityName;//城市名字
    private String cityPostion;//经纬度 x,x
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityPostion() {
        return cityPostion;
    }

    public void setCityPostion(String cityPostion) {
        this.cityPostion = cityPostion;
    }

}
