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
    void cityInsert(CityBean...cityBeans);

    @Delete
    void cityDelete(CityBean cityBean);


    @Update
    void cityUpdate(CityBean cityBean);

    @Query("SELECT * FROM  CityBean " )
    List<CityBean> cityQueryAll();

    @Query("SELECT * FROM CityBean WHERE cityName = :name")
   CityBean cityQueryBycityName(String name);
}
