package com.mojota.succulent.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 笔记操作状态，例如赞
 *
 * @author jamie
 * @date 18-9-6
 */
@Entity
@Table(name = "note_operate")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoteOperate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // 自增id

    @Column(nullable = false)
    Long noteId;

    @Column(nullable = false)
    Integer userId;

    @Column(nullable = false, columnDefinition = "int default 0")
    Integer isLikey; // 0未赞 1已赞

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsLikey() {
        return isLikey;
    }

    public void setIsLikey(Integer isLikey) {
        this.isLikey = isLikey;
    }
}
