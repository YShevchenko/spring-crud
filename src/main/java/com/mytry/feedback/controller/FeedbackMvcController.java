package com.mytry.feedback.controller;

import com.mytry.feedback.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FeedbackMvcController {

    private FeedbackService feedbackService;

    public FeedbackMvcController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping(path = "/")
    public String getProducts(Model model) {
        model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
        return "index";
    }
}
