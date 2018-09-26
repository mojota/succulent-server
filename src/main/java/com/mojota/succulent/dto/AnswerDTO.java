package com.mojota.succulent.dto;

import com.mojota.succulent.entity.User;

/**
 * @author jamie
 * @date 18-9-25
 */
public interface AnswerDTO {

    User userInfo = null;

    Long answerId = null;//回答id;
    Long questionId = null; // 问题id;
    String answerContent = "";//回答正文;
    Long answerTime = null;//回答时间;
    Integer upCount = 0;//回答点赞数;
    Integer isUp = 0;//点赞状态，0：未点，1：已点;

    User getUserInfo();

    Long getAnswerId();

    String getAnswerContent();

    Long getAnswerTime();

    Integer getUpCount();

    Integer getIsUp();
}
