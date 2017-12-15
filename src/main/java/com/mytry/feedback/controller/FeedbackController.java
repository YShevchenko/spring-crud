package com.mytry.feedback.controller;

import com.mytry.feedback.entity.Feedback;
import com.mytry.feedback.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "api/feedbacks")
public class FeedbackController {

    private FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Feedback> getFeedback(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(feedbackService.getFeedback(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        return new ResponseEntity<>(feedbackService.getAllFeedbacks(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Feedback> addFeedback(@Valid @RequestBody Feedback feedback) {
        return new ResponseEntity<>(feedbackService.saveFeedback(feedback), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Feedback> replaceFeedback(@Valid @RequestBody Feedback feedback,
                                                    @PathVariable("id") Integer id) {
        Feedback updatedFeedback = feedbackService.updateFeedback(feedback, id);
        if (updatedFeedback == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedFeedback, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Feedback> deleteFeedback(@PathVariable("id") Integer id) {
        feedbackService.deleteFeedback(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
