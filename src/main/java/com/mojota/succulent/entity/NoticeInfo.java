package com.mojota.succulent.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 通知
 * Created by jamie on 18-12-25
 */
@Entity
@Table(name = "notice")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long noticeId;//通知id

    @Column
    String noticeTitle;// 标题

    @Column(columnDefinition = "TEXT")
    String noticeContent;// 内容

    @Column
    Long noticeTime;// 时间

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Long getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Long noticeTime) {
        this.noticeTime = noticeTime;
    }
}
