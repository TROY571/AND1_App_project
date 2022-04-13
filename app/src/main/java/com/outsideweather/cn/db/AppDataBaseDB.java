package com.outsideweather.cn.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.outsideweather.cn.Bean.CityBean;
import com.outsideweather.cn.Bean.NoteBean;
import com.outsideweather.cn.dao.CityDao;
import com.outsideweather.cn.dao.NoteDao;


@Database(entities = {NoteBean.class, CityBean.class}, version = 1, exportSchema = false)
public abstract  class  AppDataBaseDB extends RoomDatabase {
    public  abstract NoteDao noteDao();
    public  abstract CityDao  cityDao();
}
