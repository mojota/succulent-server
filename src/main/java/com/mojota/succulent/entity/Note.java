package com.mojota.succulent.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;

/**
 * 笔记
 *
 * @author jamie
 * @date 18-9-6
 */
@Entity
@Table(name = "note")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long noteId;//笔记id

    @Column(nullable = false)
    int noteType; // 笔记类型 1-成长笔记 2-造景

    @Column
    String noteTitle;// 标题

    @Column
    long updateTime;// 更新时间

    @Column
    int permission; // 0保密 1公开

    @Column
    int likeyCount; // 赞数

    @Column(columnDefinition = "TEXT")
    String picUrls; // 封面图片地址们,以;为分隔符保存

    @Column(nullable = false)
    int userId; // 发布用户id

//    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "note")
//    List<NoteDetail> noteDetails;

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public int getNoteType() {
        return noteType;
    }

    public void setNoteType(int noteType) {
        this.noteType = noteType;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public int getLikeyCount() {
        return likeyCount;
    }

    public void setLikeyCount(int likeyCount) {
        this.likeyCount = likeyCount;
    }

    public String getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
