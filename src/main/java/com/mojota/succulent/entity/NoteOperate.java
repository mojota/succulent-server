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
    long id; // 自增id

    @Column(nullable = false)
    long noteId;

    @Column(nullable = false)
    int userId;

    @Column(nullable = false, columnDefinition = "int default 0")
    int isLike; // 0未赞 1已赞

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }
}
