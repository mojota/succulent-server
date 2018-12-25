package com.mojota.succulent.dao;

import com.mojota.succulent.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jamie
 * @date 18-12-25
 */
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

}
