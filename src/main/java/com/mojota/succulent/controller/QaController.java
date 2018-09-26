package com.mojota.succulent.controller;

import com.mojota.succulent.dto.AnswerDTO;
import com.mojota.succulent.dto.QuestionDTO;
import com.mojota.succulent.dto.ResponseInfo;
import com.mojota.succulent.entity.Answer;
import com.mojota.succulent.entity.Question;
import com.mojota.succulent.service.QaService;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jamie
 * @date 18-9-18
 */
@RestController
@RequestMapping("/qa")
public class QaController {

    @Autowired
    private QaService qaService;

    private void checkUser(Integer userId) throws BusinessException {
        // userId不可为空
        if (userId == null || userId == 0) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_ERROR_USER_NOT_LOGIN);
        }
    }

    /**
     * 提问
     */
    @PostMapping(value = "/qaAdd")
    public ResponseInfo qaAdd(@RequestParam Integer userId,
                              @RequestParam String questionTitle,
                              @RequestParam String questionPicUrl) throws BusinessException {
        checkUser(userId);

        long time = System.currentTimeMillis();
        Question question = new Question();
        question.setUserId(userId);
        question.setQuestionTime(time);
        question.setQuestionTitle(questionTitle);
        question.setQuestionPicUrl(questionPicUrl);
        qaService.qaAdd(question);
        return ResponseUtil.success(null);
    }

    /**
     * 回答
     */
    @RequestMapping(value = "/answerAdd")
    public ResponseInfo answerAdd(@RequestParam Integer userId,
                                  @RequestParam Long questionId,
                                  @RequestParam String answerContent) throws BusinessException {
        checkUser(userId);

        long time = System.currentTimeMillis();
        Answer answer = new Answer();
        answer.setUserId(userId);
        answer.setAnswerContent(answerContent);
        answer.setAnswerTime(time);
        answer.setQuestionId(questionId);
        qaService.answerAdd(answer);

        return ResponseUtil.success(null);
    }

    /**
     * 获取问题列表
     */
    @PostMapping(value = "/getQaList")
    public ResponseInfo getQaList(@RequestParam(required = false) Long questionTime,
                                  @PageableDefault(page = 0, size = 1) Pageable
                                          pageable) throws BusinessException {

        if (questionTime == null) {
            questionTime = System.currentTimeMillis();
        }
        if (pageable.getPageSize() == 1) { // 如果不传size，就取全部
            pageable = null;
        }

        List<QuestionDTO> list = qaService.getQaList(questionTime, pageable);
        return ResponseUtil.success(list, pageable);
    }

    /**
     * 获取回答列表
     */
    @PostMapping(value = "/getAnswerList")
    public ResponseInfo getAnswerList(@RequestParam Integer userId,
                                      @RequestParam Long questionId,
                                      @RequestParam(required = false) Long answerTime,
                                      @PageableDefault(page = 0, size = 1) Pageable
                                              pageable) {
        if (answerTime == null) {
            answerTime = System.currentTimeMillis();
        }
        if (pageable.getPageSize() == 1) { // 如果不传size，就取全部
            pageable = null;
        }

        List<AnswerDTO> list = qaService.getAnswerList(userId, questionId,
                answerTime, pageable);
        return ResponseUtil.success(list, pageable);
    }


    /**
     * 回答顶
     */
    @PostMapping(value = "/answerUp")
    public ResponseInfo answerUp(@RequestParam Integer userId,
                                 @RequestParam Long answerId, @RequestParam
                                         Integer isUp) throws
            BusinessException {
        checkUser(userId);
        if (answerId == null) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_DATA_NOT_FOUND);
        }
        qaService.answerUp(userId, answerId, isUp);
        return ResponseUtil.success(null);
    }
}
