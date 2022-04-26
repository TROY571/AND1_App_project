package com.outsideweather.cn.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.outsideweather.cn.Bean.NoteBean;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void noteInsert(NoteBean...noteModels);

    @Delete
    void noteDelete(NoteBean noteModel);


    @Update
    void noteUpdate(NoteBean noteModel);

    @Query("SELECT * FROM  NoteBean" )
    List<NoteBean> noteQueryAll();

    @Query("SELECT * FROM NoteBean WHERE name LIKE '%'||:name ||'%' ")
    List<NoteBean> noteQueryByNoteName(String name);

    @Query("SELECT * FROM NoteBean WHERE uid = :uid")
    NoteBean  noteQueryByUid(int uid);
}
