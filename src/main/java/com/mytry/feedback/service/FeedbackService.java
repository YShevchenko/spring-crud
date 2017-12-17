package com.mytry.feedback.service;

import com.mytry.feedback.entity.Feedback;
import com.mytry.feedback.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    private FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback getFeedback(Integer id) {
        return feedbackRepository.findOne(id);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Integer id) {
        feedbackRepository.delete(id);
    }

    public Feedback updateFeedback(Feedback feedbackToUpdate, Integer id) {
        Feedback foundFeedback = feedbackRepository.findOne(id);
        if (foundFeedback != null) {
            feedbackToUpdate.setId(id);
            return feedbackRepository.save(feedbackToUpdate);
        } else {
            return foundFeedback;
        }
    }

    public List<Feedback> findBySubjectContains(String subject) {
        return feedbackRepository.findBySubjectContains(subject);
    }
}
