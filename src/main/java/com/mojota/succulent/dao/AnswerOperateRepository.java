package com.mojota.succulent.dao;

import com.mojota.succulent.entity.AnswerOperate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jamie
 * @date 18-9-26
 */
public interface AnswerOperateRepository extends JpaRepository<AnswerOperate, Long> {

    AnswerOperate findByUserIdAndAnswerId(Integer userId, Long answerId);

    int countByAnswerIdAndIsUp(Long answerId, Integer isUp);
}
