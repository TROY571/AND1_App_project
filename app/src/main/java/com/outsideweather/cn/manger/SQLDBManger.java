package com.outsideweather.cn.manger;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.outsideweather.cn.Model.CityModel;
import com.outsideweather.cn.Model.NoteModel;
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


    public static  void addCity(CityModel cityModel){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //使用ContentValues来对要添加的数据进行组装。
        ContentValues values = new ContentValues();
        //开始组装第一条数据
        values.put("cityName",cityModel.getCityName());
        values.put("cityPostion",cityModel.getCityPostion());
        db.insert("CITY",null,values);//插入第一条数据
        db.close();
    }

    public static  void deleteCity(String id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("CITY","id='"+id+"'",null);
    }

    public static  List<CityModel> getCityList(){
        List<CityModel> cityModelArrayList=new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("CITY",null,null,null,null,null,"id desc",null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range")  String cityName = cursor.getString(cursor.getColumnIndex("cityName"));
                @SuppressLint("Range") String cityPostion = cursor.getString(cursor.getColumnIndex("cityPostion"));
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                CityModel cityModel=new CityModel();
                cityModel.setId(id);
                cityModel.setCityName(cityName);
                cityModel.setCityPostion(cityPostion);
                cityModelArrayList.add(cityModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return cityModelArrayList;
    }
    public static  CityModel getCity(String ids){
        CityModel cityModel=new CityModel();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("CITY",null,"cityName='"+ids+"'",null,null,null,"",null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range")  String cityName = cursor.getString(cursor.getColumnIndex("cityName"));
                @SuppressLint("Range") String cityPostion = cursor.getString(cursor.getColumnIndex("cityPostion"));
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                cityModel.setId(id);
                cityModel.setCityName(cityName);
                cityModel.setCityPostion(cityPostion);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return cityModel;
    }





    public static  void addNote(NoteModel noteModel){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //使用ContentValues来对要添加的数据进行组装。
        ContentValues values = new ContentValues();
        //开始组装第一条数据
        values.put("noteName",noteModel.getNoteName());
        values.put("noteContent",noteModel.getNoteContent());
        values.put("time",noteModel.getTime());
        db.insert("NOTE",null,values);//插入第一条数据
        db.close();
    }

    public static  void updateNote(NoteModel noteModel){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //使用ContentValues来对要添加的数据进行组装。
        ContentValues values = new ContentValues();
        //开始组装第一条数据
        values.put("noteName",noteModel.getNoteName());
        values.put("noteContent",noteModel.getNoteContent());
        db.update("NOTE",values,"id='"+noteModel.getUid()+"'",null);
    }

    public static  void deleteNote(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("NOTE","id='"+id+"'",null);
    }

    public static  List<NoteModel> noteQueryByNoteName(String text){
        List<NoteModel> userModelList=new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
      //  "noteName LIKE '%"+text+"%'"
        Cursor cursor = db.query("NOTE",null,"noteName LIKE '%"+text+"%'",null,null,null,"",null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range")  String noteName = cursor.getString(cursor.getColumnIndex("noteName"));
                @SuppressLint("Range") String noteContent = cursor.getString(cursor.getColumnIndex("noteContent"));
                @SuppressLint("Range")  String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                NoteModel userModel=new NoteModel();
                userModel.setUid(id);
                userModel.setNoteName(noteName);
                userModel.setNoteContent(noteContent);
                userModel.setTime(time);
                userModelList.add(userModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return userModelList;
    }


    public static  List<NoteModel> getNoteList(){
        List<NoteModel> userModelList=new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("NOTE",null,null,null,null,null,"id desc",null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range")  String noteName = cursor.getString(cursor.getColumnIndex("noteName"));
                @SuppressLint("Range") String noteContent = cursor.getString(cursor.getColumnIndex("noteContent"));
                @SuppressLint("Range")  String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                NoteModel userModel=new NoteModel();
                userModel.setUid(id);
                userModel.setNoteName(noteName);
                userModel.setNoteContent(noteContent);
                userModel.setTime(time);
                userModelList.add(userModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return userModelList;
    }


    public static  NoteModel getNote(String ids){
        NoteModel noteModel=new NoteModel();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//查询Book中所有的数据
        Cursor cursor = db.query("NOTE",null,"id='"+ids+"'",null,null,null,"",null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range")  String noteName = cursor.getString(cursor.getColumnIndex("noteName"));
                @SuppressLint("Range") String noteContent = cursor.getString(cursor.getColumnIndex("noteContent"));
                @SuppressLint("Range")  String time = cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                noteModel.setUid(id);
                noteModel.setNoteName(noteName);
                noteModel.setNoteContent(noteContent);
                noteModel.setTime(time);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return noteModel;
    }





}
