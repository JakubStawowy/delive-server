package com.example.rentiaserver.data.controllers.feedback;

import com.example.rentiaserver.data.dao.FeedbackRepository;
import com.example.rentiaserver.data.po.FeedbackPo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(FeedbackManageController.BASE_ENDPOINT)
public final class FeedbackManageController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/feedback";
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackManageController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @PostMapping(path = EndpointConstants.ADD_FEEDBACK_ENDPOINT, consumes = "application/json")
    public FeedbackPo addFeedback(@RequestBody FeedbackPo feedback) {
        return feedbackRepository.save(feedback);
    }


    @DeleteMapping(EndpointConstants.DELETE_FEEDBACK_ENDPOINT)
    public ResponseEntity<?> deleteFeedback(@PathVariable("id") Long id) {
        try{
            feedbackRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException ignored) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = EndpointConstants.EDIT_FEEDBACK_ENDPOINT, consumes = "application/json")
    public ResponseEntity<?> editFeedback(@PathVariable("id") Long id, @RequestBody FeedbackPo feedback) {
        Optional<FeedbackPo> optionalFeedback = feedbackRepository.findById(id);
        if(optionalFeedback.isPresent()) {
            feedback.setId(id);
            feedbackRepository.save(feedback);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
