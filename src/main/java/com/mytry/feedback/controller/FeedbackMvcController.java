package com.mytry.feedback.controller;

import com.mytry.feedback.entity.Feedback;
import com.mytry.feedback.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FeedbackMvcController {

    private FeedbackService feedbackService;

    public FeedbackMvcController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping(path = "/")
    public String redirectFromRootPage() {
        return "redirect:/feedbacks";
    }

    @GetMapping(path = "/feedbacks")
    public String getAllFeedbacks(Model model) {
        model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
        return "feedbacks";
    }

    @GetMapping(path = "/feedbacks/{id}")
    public String getFeedback(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("feedback", feedbackService.getFeedback(id));
        return "post";
    }

    @GetMapping(path = "/feedbacks/submit")
    public String createFeedback(Model model) {
        model.addAttribute("feedback", new Feedback());
        return "submit";
    }

    @PostMapping(path = "/feedbacks")
    public String submitFeedback(Feedback feedback) {
        feedbackService.saveFeedback(feedback);
        return "redirect:/feedbacks";
    }

    @GetMapping(path = "/feedbacks/search")
    public String searchFeedbacksBySubject(Model model, @RequestParam("query") String subject) {
        model.addAttribute("feedbacks", feedbackService.searchBySubjectContains(subject));
        return "feedbacks";
    }


}
