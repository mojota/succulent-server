package com.mojota.succulent.service;

import com.mojota.succulent.dao.AnswerOperateRepository;
import com.mojota.succulent.dao.AnswerRepository;
import com.mojota.succulent.dao.QuestionRepository;
import com.mojota.succulent.dto.AnswerDTO;
import com.mojota.succulent.dto.QuestionDTO;
import com.mojota.succulent.entity.Answer;
import com.mojota.succulent.entity.AnswerOperate;
import com.mojota.succulent.entity.Question;
import com.mojota.succulent.utils.BusinessException;
import com.mojota.succulent.utils.CodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author jamie
 * @date 18-9-18
 */
@Service
public class QaService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerOperateRepository answerOperateRepository;

    public void qaAdd(Question question) {
        questionRepository.save(question);
    }

    public List<QuestionDTO> getQaList(Long questionTime, Pageable pageable) {
        return questionRepository.findQaList(questionTime, pageable);
    }

    public void answerAdd(Answer answer) {
        answerRepository.save(answer);

        Long questionId = answer.getQuestionId();
        Question question = questionRepository.findByQuestionId(questionId);
        int answerCount = answerRepository.countByQuestionId(questionId);
        question.setAnswerCount(answerCount);
        questionRepository.saveAndFlush(question);
    }

    public List<AnswerDTO> getAnswerList(Integer userId, Long questionId,
                                         Long answerTime, Pageable pageable) {
        return answerRepository.findAnswerList(userId, questionId, answerTime,
                pageable);
    }

    public void answerUp(Integer userId, Long answerId, Integer isUp) throws BusinessException {
        AnswerOperate answerOperate =
                answerOperateRepository.findByUserIdAndAnswerId(userId, answerId);
        if (answerOperate != null) {
            answerOperate.setIsUp(isUp);
        } else {
            answerOperate = new AnswerOperate();
            answerOperate.setUserId(userId);
            answerOperate.setAnswerId(answerId);
            answerOperate.setIsUp(isUp);
        }
        answerOperateRepository.saveAndFlush(answerOperate);

        Answer answer = answerRepository.findByAnswerId(answerId);
        if (answer == null) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_DATA_NOT_FOUND);
        }
        int newUpCount = answerOperateRepository.countByAnswerIdAndIsUp(answerId, 1);
        answer.setUpCount(newUpCount);
        answerRepository.saveAndFlush(answer);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteQuestion(Integer userId, Long questionId) throws BusinessException {
        // 先删回答表，再删问题表
        answerRepository.deleteByQuestionId(questionId);
        if (questionRepository.deleteByQuestionIdAndUserId(questionId, userId) <= 0) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_DATA_NOT_FOUND);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteAnswer(Integer userId, Long answerId, Long questionId) throws BusinessException {
        if (answerRepository.deleteByAnswerIdAndUserId(answerId, userId) <= 0) {
            throw new BusinessException(CodeConstants.CODE_BUSINESS_ERROR,
                    CodeConstants.MSG_BUSINESS_DATA_NOT_FOUND);
        } else {
            Question question = questionRepository.findByQuestionId(questionId);
            int answerCount = answerRepository.countByQuestionId(questionId);
            question.setAnswerCount(answerCount);
            questionRepository.saveAndFlush(question);
        }
    }
}
