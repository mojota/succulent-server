package com.mojota.succulent.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 回答
 *
 * @author jamie
 * @date 18-9-25
 */
@Entity
@Table(name = "answer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long answerId;//回答id;

    @Column(nullable = false)
    Long questionId; // 问题id;

    @Column(nullable = false, columnDefinition = "TEXT")
    String answerContent;//回答正文;

    @Column
    Long answerTime;//回答时间;

    @Column
    int upCount;//回答点赞数;

    @Column(nullable = false)
    int userId; // 发布用户id

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Long getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Long answerTime) {
        this.answerTime = answerTime;
    }

    public int getUpCount() {
        return upCount;
    }

    public void setUpCount(int upCount) {
        this.upCount = upCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
