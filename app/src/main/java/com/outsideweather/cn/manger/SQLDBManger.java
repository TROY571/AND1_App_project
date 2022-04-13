package com.outsideweather.cn.manger;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.outsideweather.cn.Bean.CityBean;
import com.outsideweather.cn.Bean.NoteBean;
import com.outsideweather.cn.db.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLDBManger {

    public static String TAG="SQLDBManger";

    public static MyDatabaseHelper dbHelper;




    public static synchronized MyDatabaseHelper createDb(Context context){
         if(dbHelper==null){
             dbHelper = new MyDatabaseHelper(context,"note.db",null,1);

         }
             return dbHelper;


    }


    public static  void addCity(CityBean cityBean){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //使用ContentValues来对要添加的数据进行组装。
        ContentValues values = new ContentValues();
        //开始组装第一条数据
        values.put("cityName", cityBean.getCityName());
        values.put("cityPostion", cityBean.getCityPostion());
        db.insert("CITY",null,values);//插入第一条数据
        db.close();
    }

    public static  void deleteCity(String id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("CITY","id='"+id+"'",null);
    }

    public static  List<CityBean> getCityList(){
        List<CityBean> cityBeanArrayList =new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("CITY",null,null,null,null,null,"id desc",null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range")  String cityName = cursor.getString(cursor.getColumnIndex("cityName"));
                @SuppressLint("Range") String cityPostion = cursor.getString(cursor.getColumnIndex("cityPostion"));
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                CityBean cityBean =new CityBean();
                cityBean.setId(Integer.valueOf(id));
                cityBean.setCityName(cityName);
                cityBean.setCityPostion(cityPostion);
                cityBeanArrayList.add(cityBean);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return cityBeanArrayList;
    }
    public static CityBean getCity(String ids){
        CityBean cityBean =new CityBean();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("CITY",null,"cityName='"+ids+"'",null,null,null,"",null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range")  String cityName = cursor.getString(cursor.getColumnIndex("cityName"));
                @SuppressLint("Range") String cityPostion = cursor.getString(cursor.getColumnIndex("cityPostion"));
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                cityBean.setId(Integer.valueOf(id));
                cityBean.setCityName(cityName);
                cityBean.setCityPostion(cityPostion);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return cityBean;
    }





    public static  void addNote(NoteBean noteBean){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //使用ContentValues来对要添加的数据进行组装。
        ContentValues values = new ContentValues();
        //开始组装第一条数据
        values.put("noteName", noteBean.getNoteName());
        values.put("noteContent", noteBean.getNoteContent());
        values.put("time", noteBean.getTime());
        db.insert("NOTE",null,values);//插入第一条数据
        db.close();
    }

    public static  void updateNote(NoteBean noteBean){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //使用ContentValues来对要添加的数据进行组装。
        ContentValues values = new ContentValues();
        //开始组装第一条数据
        values.put("noteName", noteBean.getNoteName());
        values.put("noteContent", noteBean.getNoteContent());
        db.update("NOTE",values,"id='"+ noteBean.getUid()+"'",null);
    }

    public static  void deleteNote(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("NOTE","id='"+id+"'",null);
    }

    public static  List<NoteBean> noteQueryByNoteName(String text){
        List<NoteBean> userModelList=new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
      //  "noteName LIKE '%"+text+"%'"
        Cursor cursor = db.query("NOTE",null,"noteName LIKE '%"+text+"%'",null,null,null,"",null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range")  String noteName = cursor.getString(cursor.getColumnIndex("noteName"));
                @SuppressLint("Range") String noteContent = cursor.getString(cursor.getColumnIndex("noteContent"));
                @SuppressLint("Range")  String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                NoteBean userModel=new NoteBean();
                userModel.setUid(Integer.valueOf(id));
                userModel.setNoteName(noteName);
                userModel.setNoteContent(noteContent);
                userModel.setTime(time);
                userModelList.add(userModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return userModelList;
    }


    public static  List<NoteBean> getNoteList(){
        List<NoteBean> userModelList=new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("NOTE",null,null,null,null,null,"id desc",null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range")  String noteName = cursor.getString(cursor.getColumnIndex("noteName"));
                @SuppressLint("Range") String noteContent = cursor.getString(cursor.getColumnIndex("noteContent"));
                @SuppressLint("Range")  String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                NoteBean userModel=new NoteBean();
                userModel.setUid(Integer.valueOf(id));
                userModel.setNoteName(noteName);
                userModel.setNoteContent(noteContent);
                userModel.setTime(time);
                userModelList.add(userModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return userModelList;
    }


    public static NoteBean getNote(String ids){
        NoteBean noteBean =new NoteBean();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//查询Book中所有的数据
        Cursor cursor = db.query("NOTE",null,"id='"+ids+"'",null,null,null,"",null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range")  String noteName = cursor.getString(cursor.getColumnIndex("noteName"));
                @SuppressLint("Range") String noteContent = cursor.getString(cursor.getColumnIndex("noteContent"));
                @SuppressLint("Range")  String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                noteBean.setUid(Integer.valueOf(id));
                noteBean.setNoteName(noteName);
                noteBean.setNoteContent(noteContent);
                noteBean.setTime(time);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return noteBean;
    }





}
