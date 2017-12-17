package com.mytry.feedback.service;

import com.mytry.feedback.entity.Feedback;
import com.mytry.feedback.repository.FeedbackRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FeedbackService.class)
public class FeedbackServiceTest {

    private static final Integer FEEDBACK_EXISTING_ID = 1;
    private static final Integer FEEDBACK_NOT_EXISTING_ID = 999;

    private Feedback feedback;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackService feedbackService;

    @Before
    public void setUp() throws Exception {
        feedback = new Feedback();
        feedback.setAuthor("John Doe");
        feedback.setContent("Nice place");
        feedback.setSubject("Pub prancing pony");
        feedback.setRecommend(true);
        feedback.setSummary("Nice");

        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(feedback);

        Mockito.when(feedbackRepository.save(feedback)).thenReturn(feedback);
        Mockito.when(feedbackRepository.findBySubjectContains(feedback.getSubject())).thenReturn(feedbacks);
        Mockito.when(feedbackRepository.findOne(FEEDBACK_EXISTING_ID)).thenReturn(feedback);
        Mockito.when(feedbackRepository.findOne(FEEDBACK_NOT_EXISTING_ID)).thenReturn(null);
        Mockito.when(feedbackRepository.findAll()).thenReturn(feedbacks);
    }

    @Test
    public void shouldCreateUser() {
        Feedback result = feedbackService.saveFeedback(feedback);
        assertThat(result, new ReflectionEquals(feedback));
    }

    @Test
    public void shouldFindFeedbackBySubject() {
        List<Feedback> result = feedbackService.findBySubjectContains(feedback.getSubject());
        assertThat(result, hasSize(1));
        assertThat(result, hasItems(feedback));
    }

    @Test
    public void shouldReturnSingleFeedback() {
        assertThat(feedbackService.getFeedback(FEEDBACK_EXISTING_ID), new ReflectionEquals(feedback));
    }

    @Test
    public void shouldReturnAllFeedbacks() {
        List<Feedback> result = feedbackService.getAllFeedbacks();
        assertThat(result, hasSize(1));
        assertThat(result, hasItems(feedback));
    }

    @Test
    public void shouldUpdateFeedbackIfFound() {
        Feedback result = feedbackService.updateFeedback(feedback, FEEDBACK_EXISTING_ID);
        assertThat(result, new ReflectionEquals(feedback));
    }

    @Test
    public void shouldNotUpdateFeedbackIfNotFound() {
        Feedback result = feedbackService.updateFeedback(feedback, FEEDBACK_NOT_EXISTING_ID);
        assertThat(result, is(nullValue()));
    }
}
