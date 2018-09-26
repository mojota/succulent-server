package com.mojota.succulent.dto;

import com.mojota.succulent.entity.User;

/**
 * question返回
 *
 * @author jamie
 * @date 18-9-25
 */
public interface QuestionDTO {

    User userInfo = null;

    Long questionId = null;//问题id;
    String questionTitle = "";//问题;
    String questionPicUrl = ""; // 问题图片;
    Long questionTime = null;//提问时间;
    Integer answerCount = 0;//回答数;

    User getUserInfo();

    Long getQuestionId();

    String getQuestionTitle();

    String getQuestionPicUrl();

    Long getQuestionTime();

    Integer getAnswerCount();
}
