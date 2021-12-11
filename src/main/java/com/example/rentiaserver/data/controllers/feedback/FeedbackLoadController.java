package com.example.rentiaserver.data.controllers.feedback;

import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.data.to.FeedbackTo;
import com.example.rentiaserver.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(FeedbackLoadController.BASE_ENDPOINT)
public class FeedbackLoadController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/feedback";
    private final UserService userService;

    @Autowired
    public FeedbackLoadController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public List<FeedbackTo> getUserFeedback(@RequestParam Long userId) {
        return userService.getFeedbackPosByUserPoId(userId)
                .stream()
                .map(FeedbackTo::new)
                .collect(Collectors.toList());
    }
}
