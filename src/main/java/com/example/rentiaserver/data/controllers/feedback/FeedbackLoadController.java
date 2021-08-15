package com.example.rentiaserver.data.controllers.feedback;

import com.example.rentiaserver.data.dao.FeedbackRepository;
import com.example.rentiaserver.data.to.FeedbackTo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping({EndpointConstants.INDEX_ENDPOINT, EndpointConstants.INDEX_ENDPOINT_SLASH})
    public List<FeedbackTo> getAllFeedback() {
        List<FeedbackTo> result = new ArrayList<>();
        feedbackRepository.findAll().forEach(feedback -> result.add(new FeedbackTo(feedback)));
        return result;
    }


    @GetMapping(EndpointConstants.USER_FEEDBACK_ENDPOINT)
    public List<FeedbackTo> getUserFeedback(@PathVariable("id") Long id) {
        List<FeedbackTo> result = new ArrayList<>();
        feedbackRepository.getFeedbacksByUserId(id).forEach(feedback -> result.add(new FeedbackTo(feedback)));

        return result;
    }
}
