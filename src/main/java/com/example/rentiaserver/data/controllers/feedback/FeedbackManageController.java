package com.example.rentiaserver.data.controllers.feedback;

import com.example.rentiaserver.data.po.FeedbackPo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.services.UserService;
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
    private final UserService userService;

    @Autowired
    public FeedbackManageController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = EndpointConstants.ADD_FEEDBACK_ENDPOINT, consumes = "application/json")
    public void addFeedback(@RequestBody FeedbackPo feedback) {
        userService.saveFeedback(feedback);
    }

    @DeleteMapping(EndpointConstants.DELETE_FEEDBACK_ENDPOINT)
    public ResponseEntity<?> deleteFeedback(@PathVariable("id") Long id) {
        try{
            userService.deleteFeedbackById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException ignored) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = EndpointConstants.EDIT_FEEDBACK_ENDPOINT, consumes = "application/json")
    public ResponseEntity<?> editFeedback(@PathVariable("id") Long id, @RequestBody FeedbackPo feedback) {
        Optional<FeedbackPo> optionalFeedback = userService.findFeedbackById(id);
        if(optionalFeedback.isPresent()) {
            feedback.setId(id);
            userService.saveFeedback(feedback);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
