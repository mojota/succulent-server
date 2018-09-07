package com.mojota.succulent.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;

/**
 * 笔记详情
 *
 * @author jamie
 * @date 18-9-6
 */

@Entity
@Table(name = "note_detail")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoteDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer detailId;//详情id

    @Column(nullable = false)
    Long noteId;// 笔记id

    @Column(columnDefinition = "TEXT")
    String content;// 笔记内容

    @Column
    long createTime;// 记录时间

    @Column(columnDefinition = "TEXT")
    String strPicUrls; // 图片地址们,以;为分隔符保存

    @Transient
    List<String> picUrls;// 图片地址们, 返回用

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getStrPicUrls() {
        return strPicUrls;
    }

    public void setStrPicUrls(String strPicUrls) {
        this.strPicUrls = strPicUrls;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }
}
