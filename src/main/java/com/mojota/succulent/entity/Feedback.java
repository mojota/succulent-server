package com.mojota.succulent.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 意见反馈
 *
 * @author jamie
 * @date 18-12-25
 */
@Entity
@Table(name = "feedback")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long feedbackId;

    @Column(columnDefinition = "TEXT")
    String content;

    @Column
    Long createTime;

    @Column
    int userId;

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
