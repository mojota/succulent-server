package com.mojota.succulent.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * 问答操作状态，例如赞
 *
 * @author jamie
 * @date 18-9-25
 */
@Entity
@Table(name = "answer_operate")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerOperate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // 自增id

    @Column(nullable = false)
    Long answerId;

    @Column(nullable = false)
    Integer userId;

    @Column(nullable = false, columnDefinition = "int default 0")
    Integer isUp; // 0未赞 1已赞

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsUp() {
        return isUp;
    }

    public void setIsUp(Integer isUp) {
        this.isUp = isUp;
    }
}
