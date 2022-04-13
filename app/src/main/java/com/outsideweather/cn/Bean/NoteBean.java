package com.outsideweather.cn.Bean;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * email：
 * description：
 */
@Entity
public class NoteBean {
    /**
     * 数据库 自增的库文件 唯一的主键
     */
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "name")
    private String noteName;//记事本标题
    @ColumnInfo(name = "noteContent")
    private String noteContent;//记事本内容
    @ColumnInfo(name = "time")
    private String time;//记事本时间

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    @Ignore
    public NoteBean(){

    }


    public NoteBean(String noteName, String noteContent,String time) {
        this.noteName = noteName;
        this.noteContent = noteContent;
        this.time=time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }


}
