package com.mytry.feedback.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytry.feedback.entity.Feedback;
import com.mytry.feedback.service.FeedbackService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FeedbackController.class)
public class FeedbackControllerTest {

    private static final Integer FEEDBACK_EXISTING_ID = 1;
    private static final String FEEDBACKS_URL = "/api/feedbacks";
    private static final String EXISTING_FEEDBACK_URL = FEEDBACKS_URL + "/" + FEEDBACK_EXISTING_ID;
    private static final String NOT_EXISTING_FEEDBACK_URL = FEEDBACKS_URL + "/" + Integer.MAX_VALUE;
    private Feedback feedback;
    private List<Feedback> feedbacks;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @Before
    public void setUp() throws JsonProcessingException {
        feedback = new Feedback();
        feedback.setAuthor("John Doe");
        feedback.setContent("Nice place");
        feedback.setSubject("Pub prancing pony");
        feedback.setRecommend(true);
        feedback.setSummary("Nice");

        feedbacks = new ArrayList<>();
        feedbacks.add(feedback);

        Mockito.when(feedbackService.saveFeedback(feedback)).thenReturn(feedback);
        Mockito.when(feedbackService.findBySubjectContains(feedback.getSubject())).thenReturn(feedbacks);
        Mockito.when(feedbackService.getFeedback(FEEDBACK_EXISTING_ID)).thenReturn(feedback);
        Mockito.when(feedbackService.getAllFeedbacks()).thenReturn(feedbacks);
        Mockito.when(feedbackService.updateFeedback(feedback, FEEDBACK_EXISTING_ID)).thenReturn(feedback);
    }

    @Test
    public void shouldCreateFeedback() throws Exception {
        mockMvc.perform(post(FEEDBACKS_URL)
                .content(getFeedbackJson(feedback))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(content().json(getFeedbackJson(feedback)));
    }

    @Test
    public void shouldNotCreateFeedbackWithSubjectLess3() throws Exception {
        feedback.setSubject("12");
        mockMvc.perform(post(FEEDBACKS_URL)
                .content(getFeedbackJson(feedback))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value(equalTo("Validation failed")))
                .andExpect(jsonPath("$.fieldErrors.subject").value(equalTo("size must be between 3 and 30")));
    }

    @Test
    public void shouldGetSingleFeedback() throws Exception {
        mockMvc.perform(get(EXISTING_FEEDBACK_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(getFeedbackJson(feedback)));
    }

    @Test
    public void shouldGetAllFeedbacks() throws Exception {
        mockMvc.perform(get(FEEDBACKS_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(getFeedbackJson(feedbacks)));
    }

    @Test
    public void shouldUpdateFeedback() throws Exception {
        mockMvc.perform(put(EXISTING_FEEDBACK_URL)
                .content(getFeedbackJson(feedback))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(getFeedbackJson(feedback)));
    }

    @Test
    public void shouldUpdateFeedbackIdNotFound() throws Exception {
        mockMvc.perform(put(NOT_EXISTING_FEEDBACK_URL)
                .content(getFeedbackJson(feedback))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteFeedback() throws Exception {
        mockMvc.perform(delete(NOT_EXISTING_FEEDBACK_URL))
                .andExpect(status().isNoContent());
    }

    private String getFeedbackJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
