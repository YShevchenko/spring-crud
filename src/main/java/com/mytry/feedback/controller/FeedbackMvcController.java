package com.mytry.feedback.controller;

import com.mytry.feedback.entity.Feedback;
import com.mytry.feedback.service.FeedbackService;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/feedbacks")
public class FeedbackMvcController {

    private FeedbackService feedbackService;

    public FeedbackMvcController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public String getAllFeedbacks(Model model) {
        model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
        return "feedbacks";
    }

    @GetMapping(path = "/{id}")
    public String getFeedback(Model model, @PathVariable("id") Integer id) {
            model.addAttribute("feedback", feedbackService.getFeedback(id));
            return "post";
    }

    @GetMapping(path = "/new")
    public String createFeedback(Model model) {
        model.addAttribute("feedback", new Feedback());
        return "newfeedback";
    }

    @PostMapping
    public String submitFeedback(@Valid Feedback feedback, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "newfeedback";
        }
        feedbackService.saveFeedback(feedback);
        return "redirect:/feedbacks";
    }

    @GetMapping(path = "/search")
    public String searchFeedbacksBySubject(Model model, @RequestParam("query") String subject) {
        model.addAttribute("feedbacks", feedbackService.searchBySubjectContains(subject));
        return "feedbacks";
    }


}
