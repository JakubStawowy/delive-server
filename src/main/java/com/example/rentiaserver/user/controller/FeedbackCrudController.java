package com.example.rentiaserver.user.controller;

import com.example.rentiaserver.base.model.to.ResponseTo;
import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.user.service.FeedbackService;
import com.example.rentiaserver.user.model.to.FeedbackTo;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(FeedbackCrudController.BASE_ENDPOINT)
public final class FeedbackCrudController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/feedback";

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackCrudController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/user")
    public List<FeedbackTo> getUserFeedback(@RequestParam Long userId) {
        return feedbackService.getFeedbacksByUserId(userId);
    }

    @PostMapping(path = "/add")
    public ResponseTo addFeedback(@RequestBody FeedbackTo feedback)
            throws EntityNotFoundException {
        feedbackService.add(feedback);
        return ResponseTo.builder()
                .operationSuccess(true)
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseTo deleteFeedback(@PathVariable("id") Long id) {
        feedbackService.deleteFeedbackById(id);
        return ResponseTo.builder()
                .operationSuccess(true)
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping(value = "/edit")
    public ResponseTo editFeedback(@RequestBody FeedbackTo feedback)
            throws EntityNotFoundException {
        feedbackService.save(feedback);
        return ResponseTo.builder()
                .operationSuccess(true)
                .status(HttpStatus.OK)
                .build();
    }
}
