package com.outsideweather.cn.Model;


/**
 * email：
 * description：
 */

public class NoteModel {
    /**
     * 数据库 自增的库文件 唯一的主键
     */

    private String uid;

    private String noteName;//记事本标题

    private String noteContent;//记事本内容

    private String time;//记事本时间
    public NoteModel(String noteName, String noteContent,String time) {
        this.noteName = noteName;
        this.noteContent = noteContent;
        this.time=time;
    }

    public  NoteModel(){

    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
