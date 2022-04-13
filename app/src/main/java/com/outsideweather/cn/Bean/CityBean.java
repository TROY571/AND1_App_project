package com.outsideweather.cn.Bean;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 日期：2022/4/4
 * email：
 * description：
 */
@Entity
public class CityBean {
    @ColumnInfo(name = "cityName")
    private String cityName;//选中城市名字
    @ColumnInfo(name = "cityPostion")
    private String cityPostion;//和风城市经纬度 x,x
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
