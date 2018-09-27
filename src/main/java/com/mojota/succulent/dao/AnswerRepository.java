package com.mojota.succulent.dao;

import com.mojota.succulent.dto.AnswerDTO;
import com.mojota.succulent.entity.Answer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author jamie
 * @date 18-9-25
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> {


    @Query("select a.answerId as answerId, a.questionId as questionId, a" +
            ".answerContent as answerContent,a.answerTime as answerTime,a" +
            ".upCount as upCount,ao.isUp as isUp,u as userInfo " +
            "from Answer a inner join User u on a.userId = u.userId left join " +
            "AnswerOperate ao on a.answerId=ao.answerId and ao.userId " +
            "= ?1 where a.questionId = ?2 and a.answerTime <?3 order by a" +
            ".answerTime desc")
    List<AnswerDTO> findAnswerList(Integer userId, Long questionId,
                                   Long answerTime, Pageable pageable);

    Answer findByAnswerId(Long answerId);

    int countByQuestionId(Long questionId);

    @Modifying
    int deleteByQuestionId(Long questionId);

    @Modifying
    int deleteByAnswerIdAndUserId(Long answerId, Integer userId);

}
