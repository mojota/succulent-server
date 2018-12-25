package com.mojota.succulent.service;

import com.mojota.succulent.dao.FeedbackRepository;
import com.mojota.succulent.entity.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jamie
 * @date 18-12-25
 */
@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public void addFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }
}
