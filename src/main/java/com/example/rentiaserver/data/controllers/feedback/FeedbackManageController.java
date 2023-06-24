package com.example.rentiaserver.data.controllers.feedback;

import com.example.rentiaserver.data.enums.FeedbackRate;
import com.example.rentiaserver.data.po.FeedbackPo;
import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.data.to.FeedbackTo;
import com.example.rentiaserver.data.util.EntityNotFoundException;
import com.example.rentiaserver.delivery.dao.MessageDao;
import com.example.rentiaserver.delivery.po.MessagePo;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
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
    public void addFeedback(@RequestBody FeedbackTo feedback, HttpServletRequest request) throws EntityNotFoundException {
        final Long loggedUserId = JsonWebTokenHelper.getRequesterId(request);

        UserPo author = userService.findUserById(loggedUserId)
                .orElseThrow(() -> new EntityNotFoundException(UserPo.class, loggedUserId));

        UserPo feedbackUser = userService.findUserById(feedback.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(UserPo.class, feedback.getUserId()));

        MessagePo message = messageDao.findById(feedback.getMessageId())
                .orElseThrow(() -> new EntityNotFoundException(MessagePo.class, feedback.getMessageId()));

        FeedbackPo feedbackPo = new FeedbackPo(
                feedback.getContent(),
                FeedbackRate.getByNumberValue(feedback.getRate()),
                author,
                feedbackUser,
                message.getOrderPo());

        message.setReplied(true);
        messageDao.save(message);
        userService.saveFeedback(feedbackPo);
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
    public ResponseEntity<?> editFeedback(@PathVariable("id") Long id, @RequestBody FeedbackTo feedback) throws EntityNotFoundException {
        Optional<FeedbackPo> optionalFeedback = userService.findFeedbackById(id);

        FeedbackPo feedbackPo = optionalFeedback.orElseThrow(() -> new EntityNotFoundException(FeedbackPo.class, id));

        setFeedbackPo(feedbackPo, feedback);
        userService.saveFeedback(feedbackPo);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void setFeedbackPo(FeedbackPo feedbackPo, FeedbackTo feedback) {
        // TOOD
    }
}
