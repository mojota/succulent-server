package com.mojota.succulent.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    Long detailId;//详情id

//    @Column(nullable = false)
//    Long noteId;// 笔记id

    // 不要DELETE级联
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch =
            FetchType.LAZY)
    @JoinColumn(name = "noteId", foreignKey = @ForeignKey(name = "fk_note_id"),
            nullable = false)
    @JsonIgnore
    Note note;

    @Column(columnDefinition = "TEXT")
    String content;// 笔记内容

    @Column
    long createTime;// 记录时间

    @Column(columnDefinition = "TEXT")
    String picUrls; // 图片地址们,以;为分隔符保存

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

//    public Long getNoteId() {
//        return noteId;
//    }
//
//    public void setNoteId(Long noteId) {
//        this.noteId = noteId;
//    }

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

    public String getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }
}
