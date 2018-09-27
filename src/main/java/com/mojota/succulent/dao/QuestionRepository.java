package com.mojota.succulent.dao;

import com.mojota.succulent.dto.QuestionDTO;
import com.mojota.succulent.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author jamie
 * @date 18-9-18
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select q.questionId as questionId, q.questionTitle as questionTitle, q" +
            ".questionPicUrl as questionPicUrl,q.questionTime as questionTime,q" +
            ".answerCount as answerCount,u as userInfo from Question q inner join " +
            "User u on q.userId = u.userId where q.questionTime <?1 order by q" +
            ".questionTime desc")
    List<QuestionDTO> findQaList(Long questionTime, Pageable pageable);

    Question findByQuestionId(Long questionId);

    @Modifying
    int deleteByQuestionIdAndUserId(Long questionId, Integer userId);
}
