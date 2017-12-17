package com.mytry.feedback.controller.mvc;

import com.mytry.feedback.entity.Feedback;
import com.mytry.feedback.service.FeedbackService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils.postForm;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FeedbackMvcController.class)
public class FeedbackControllerMvcTest {

    private static final Integer FEEDBACK_EXISTING_ID = 1;
    private static final String FEEDBACKS_URL = "/feedbacks";
    private static final String NEW_FEEDBACK_URL = FEEDBACKS_URL + "/new";
    List<Feedback> feedbacks;
    private Feedback feedback;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @Before
    public void setUp() {
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
    }

    @Test
    public void shouldGetFeedback() throws Exception {
        mockMvc.perform(get(FEEDBACKS_URL + "/" + FEEDBACK_EXISTING_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("feedback", feedback))
                .andExpect(view().name("singlefeedback"));
    }

    @Test
    public void shouldGetAllFeedbacks() throws Exception {
        mockMvc.perform(get(FEEDBACKS_URL))
                .andExpect(status().isOk())
                .andExpect(model().attribute("feedbacks", feedbacks))
                .andExpect(view().name("feedbacks"));
    }

    @Test
    public void shouldSearchFeedbackBySubject() throws Exception {
        mockMvc.perform(get(FEEDBACKS_URL + "/search")
                .param("query", feedback.getSubject()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("feedbacks", feedbacks))
                .andExpect(view().name("feedbacks"));
    }

    @Test
    public void shouldReturnNewFeedback() throws Exception {
        mockMvc.perform(get(NEW_FEEDBACK_URL))
                .andExpect(status().isOk())
                .andExpect(view().name("newfeedback"))
                .andExpect(model().attribute("feedback", new Feedback()));
    }

    @Test
    public void shouldCreateFeedback() throws Exception {
        mockMvc.perform(postForm(FEEDBACKS_URL, feedback))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl(FEEDBACKS_URL));
    }

    @Test
    public void shouldNotCreateFeedbackWithNullAuthor() throws Exception {
        feedback.setAuthor(null);
        mockMvc.perform(postForm(FEEDBACKS_URL, feedback))
                .andExpect(model().attributeHasFieldErrors("feedback", "author"))
                .andExpect(view().name("newfeedback"));
    }
}
