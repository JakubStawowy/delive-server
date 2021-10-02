package com.example.rentiaserver.data.controllers.feedback;

import com.example.rentiaserver.data.dao.FeedbackRepository;
import com.example.rentiaserver.data.to.FeedbackTo;
import com.example.rentiaserver.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(FeedbackLoadController.BASE_ENDPOINT)
public class FeedbackLoadController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/feedback";
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackLoadController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping("/user")
    public List<FeedbackTo> getUserFeedback(@RequestParam Long userId) {
        return feedbackRepository.getFeedbacksByUserId(userId)
                .stream()
                .map(FeedbackTo::new)
                .collect(Collectors.toList());
    }
}
