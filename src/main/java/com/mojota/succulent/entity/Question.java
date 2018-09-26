package com.mojota.succulent.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 问题
 *
 * @author jamie
 * @date 18-9-18
 */
@Entity
@Table(name = "question")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long questionId;//问题id;

    @Column(nullable = false, columnDefinition = "TEXT")
    String questionTitle;//问题;

    @Column(columnDefinition = "TEXT")
    String questionPicUrl; // 问题图片;

    @Column
    Long questionTime;//提问时间;

    @Column
    int answerCount;//回答数;

    @Column(nullable = false)
    int userId; // 发布用户id

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionPicUrl() {
        return questionPicUrl;
    }

    public void setQuestionPicUrl(String questionPicUrl) {
        this.questionPicUrl = questionPicUrl;
    }

    public long getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(long questionTime) {
        this.questionTime = questionTime;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
