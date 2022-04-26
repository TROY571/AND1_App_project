package com.outsideweather.cn.dao;

import androidx.lifecycle.LiveData;
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
    void insertNote(NoteBean...noteModels);

    @Delete
    void deleteNote(NoteBean noteModel);


    @Update
    void updateNote(NoteBean noteModel);

    @Query("SELECT * FROM  NoteBean" )
    List<NoteBean> getAllNotes();

    @Query("SELECT * FROM NoteBean WHERE name LIKE '%'||:name ||'%' ")
    List<NoteBean> getNotesByName(String name);

    @Query("SELECT * FROM NoteBean WHERE uid = :uid")
    NoteBean getNoteByUid(int uid);
}
