package com.outsideweather.cn.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.outsideweather.cn.Bean.CityBean;
import com.outsideweather.cn.Bean.NoteBean;
import com.outsideweather.cn.dao.CityDao;
import com.outsideweather.cn.dao.NoteDao;


@Database(entities = {NoteBean.class, CityBean.class}, version = 1, exportSchema = false)
public abstract  class  AppDataBaseDB extends RoomDatabase {

    private static volatile AppDataBaseDB instance;
    public static final String DB_NAME = "notebase.db";
    public  abstract NoteDao noteDao();
    public  abstract CityDao  cityDao();

    public static synchronized AppDataBaseDB getInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context, AppDataBaseDB.class, DB_NAME).allowMainThreadQueries().build();
        }
        return instance;
    }
}
