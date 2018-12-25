package com.mojota.succulent.controller;

import com.mojota.succulent.dto.ResponseInfo;
import com.mojota.succulent.entity.Feedback;
import com.mojota.succulent.service.FeedbackService;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.ResponseUtil;
import com.mojota.succulent.utils.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jamie
 * @date 18-12-25
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 添加反馈
     */
    @PostMapping(value = "/addFeedback")
    public ResponseInfo addFeedback(@RequestParam String content,
                                    @RequestParam(required = false, defaultValue = "0") Integer userId) throws BusinessException {
        if (StringUtils.isEmpty(content)) {
            throw new BusinessException(ResultEnum.BUSINESS_DATA_EMPTY);
        }
        if (content.length() > 300){
            throw new BusinessException(ResultEnum.BUSINESS_CONTENT_TOO_LONG);
        }
        Feedback feedback = new Feedback();
        feedback.setContent(content);
        feedback.setUserId(userId);
        feedback.setCreateTime(System.currentTimeMillis());
        feedbackService.addFeedback(feedback);
        return ResponseUtil.success(null);
    }

}
