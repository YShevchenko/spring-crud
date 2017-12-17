package com.mytry.feedback.controller.mvc;

import com.mytry.feedback.entity.Feedback;
import com.mytry.feedback.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        return "singlefeedback";
    }

    @GetMapping(path = "/new")
    public String getNewFeedbackPage(Model model) {
        model.addAttribute("feedback", new Feedback());
        return "newfeedback";
    }

    @PostMapping
    public String submitFeedback(@Valid Feedback feedback, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newfeedback";
        }
        feedbackService.saveFeedback(feedback);
        return "redirect:/feedbacks";
    }

    @GetMapping(path = "/search")
    public String searchFeedbacksBySubject(Model model, @RequestParam("query") String subject) {
        model.addAttribute("feedbacks", feedbackService.findBySubjectContains(subject));
        return "feedbacks";
    }


}
