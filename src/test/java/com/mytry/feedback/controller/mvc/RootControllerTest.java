package com.mytry.feedback.controller.mvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RootController.class)
public class RootControllerTest {

    private static final String ROOT_PAGE = "/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldRedirectFromRootPageToFeedbacks() throws Exception {
        mockMvc.perform(get(ROOT_PAGE))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/feedbacks"));
    }
}
