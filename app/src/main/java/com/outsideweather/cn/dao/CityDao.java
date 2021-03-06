package com.outsideweather.cn.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.outsideweather.cn.Bean.CityBean;
import java.util.List;

/**
 * 日期：2022/4/11
 * email：
 * description：
 */
@Dao
public interface CityDao {
    @Insert
    void insertCity(CityBean city);

    @Delete
    void deleteCity(CityBean city);


    @Update
    void updateCity(CityBean city);

    @Query("SELECT * FROM  CityBean " )
    List<CityBean> getAllCities();

    @Query("SELECT * FROM CityBean WHERE cityName = :name")
   CityBean getCityByName(String name);
}
