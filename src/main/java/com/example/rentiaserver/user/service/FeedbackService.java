package com.example.rentiaserver.user.service;

import com.example.rentiaserver.delivery.api.IMessageService;
import com.example.rentiaserver.order.model.mappers.OrderMapper;
import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.order.model.to.OrderTo;
import com.example.rentiaserver.user.model.bc.FeedbackRate;
import com.example.rentiaserver.user.model.mappers.FeedbackMapper;
import com.example.rentiaserver.user.model.po.FeedbackPo;
import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.user.repository.FeedbackDao;
import com.example.rentiaserver.user.model.to.FeedbackTo;
import com.example.rentiaserver.user.model.to.UserTo;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.delivery.repository.MessageRepository;
import com.example.rentiaserver.delivery.model.po.MessagePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    private final UserService userService;

    private final FeedbackDao feedbackRepository;

    private final IMessageService messageService;

    @Autowired
    public FeedbackService(
            UserService userService,
            FeedbackDao feedbackRepository,
            IMessageService messageService) {

        this.userService = userService;
        this.feedbackRepository = feedbackRepository;
        this.messageService = messageService;
    }

    public List<FeedbackTo> getFeedbacksByUserId(Long userId) {
        return feedbackRepository.getFeedbackPosByUserPoId(userId)
                .stream()
                .map(FeedbackMapper::mapFeedbackPoToTo)
                .collect(Collectors.toList());
    }

    public void save(FeedbackPo feedback) {
        feedbackRepository.save(feedback);
    }

    public void add(FeedbackTo feedback) throws EntityNotFoundException {

        UserPo user = userService.getUserPoById(feedback.getUserId());
        UserPo author = userService.getUserPoById(feedback.getAuthorId());

        MessagePo message = Optional.ofNullable(feedback.getMessageId())
                .map(messageService::getPoByIdOrNull)
                .orElse(null);

        OrderPo order = Optional.ofNullable(message)
                .map(MessagePo::getOrderPo)
                .orElse(null);

        FeedbackPo feedbackPo = new FeedbackPo(
                feedback.getContent(),
                FeedbackRate.getByNumberValue(feedback.getRate()),
                author,
                user,
                order);

        save(feedbackPo);

        if (message != null) {
            message.setReplied(true);
            messageService.saveMessage(message);
        }
    }

    public void deleteFeedbackById(Long id) {
        feedbackRepository.deleteById(id);
    }

    public void save(FeedbackTo feedback) throws EntityNotFoundException {

        Long feedbackId = feedback.getId();
        FeedbackPo feedbackPo = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new EntityNotFoundException(FeedbackPo.class, feedbackId));

        feedbackPo.setContent(feedback.getContent());
        feedbackPo.setRate(FeedbackRate.getByNumberValue(feedback.getRate()));

        save(feedbackPo);
    }
}
