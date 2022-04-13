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

    @Query("select * from  NoteBean " )
    List<NoteBean> noteQueryAll();

//    @Query("select * from NoteBean where name = :name")
//    List<NoteBean> noteQueryByNoteName(String name);
    @Query("select * from NoteBean where name like '%'||:name ||'%' ")
    List<NoteBean> noteQueryByNoteName(String name);

    @Query("select * from NoteBean where uid = :uid")
    NoteBean  noteQueryByUid(int uid);
}
