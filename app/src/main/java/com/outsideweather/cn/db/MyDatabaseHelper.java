package com.outsideweather.cn.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    /**
     * 我的记事本
     */
    public static final String CREATE_NOTE = "Create table NOTE("
            +"id integer primary key autoincrement,"
            +"noteName text,"
            +"noteContent text,"
            +"time text)";
    /**
     * 我的城市
     */
    public static final String CREATE_CITY = "Create table CITY("
            +"id integer primary key autoincrement,"
            +"cityName text,"
            +"cityPostion text)";
    private Context mContext;//?
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTE);
        db.execSQL(CREATE_CITY);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists NOTE");
        db.execSQL("drop table if exists CITY");
        onCreate(db);
    }

}

