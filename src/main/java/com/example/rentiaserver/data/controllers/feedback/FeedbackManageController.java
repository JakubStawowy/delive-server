package com.example.rentiaserver.data.controllers.feedback;

import com.example.rentiaserver.data.enums.FeedbackRate;
import com.example.rentiaserver.data.po.FeedbackPo;
import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.data.to.FeedbackTo;
import com.example.rentiaserver.delivery.dao.MessageDao;
import com.example.rentiaserver.delivery.po.MessagePo;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(FeedbackManageController.BASE_ENDPOINT)
public final class FeedbackManageController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/feedback";
    private final UserService userService;
    private final MessageDao messageDao;

    @Autowired
    public FeedbackManageController(UserService userService, MessageDao messageDao) {
        this.userService = userService;
        this.messageDao = messageDao;
    }

    @PostMapping(path = "/add")
    public void addFeedback(@RequestBody FeedbackTo feedback, HttpServletRequest request) {
        final Long loggedUserId = JsonWebTokenHelper.getRequesterId(request);
        Optional<UserPo> optionalAuthorPo = userService.findUserById(loggedUserId);
        Optional<UserPo> optionalUserPo = userService.findUserById(feedback.getUserId());
        Optional<MessagePo> optionalMessagePo = messageDao.findById(feedback.getMessageId());

        if (optionalUserPo.isPresent() && optionalAuthorPo.isPresent() && optionalMessagePo.isPresent()) {

            MessagePo messagePo = optionalMessagePo.get();
            FeedbackPo feedbackPo = new FeedbackPo(
                    feedback.getContent(),
                    FeedbackRate.getByNumberValue(feedback.getRate()),
                    optionalAuthorPo.get(),
                    optionalUserPo.get(),
                    messagePo.getOrderPo());
            messagePo.setReplied(true);
            messageDao.save(messagePo);
            userService.saveFeedback(feedbackPo);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteFeedback(@PathVariable("id") Long id) {
        try{
            userService.deleteFeedbackById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException ignored) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}/edit")
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
