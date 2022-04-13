package com.outsideweather.cn.db;

import android.content.Context;

import androidx.room.Room;

/**
 * email：
 * description：
 */
public class DBManger {
    public static volatile AppDataBaseDB instance;
    public static final String DB_NAME = "notebase.db";
    public static synchronized AppDataBaseDB getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public static AppDataBaseDB create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDataBaseDB.class,
                DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

}
