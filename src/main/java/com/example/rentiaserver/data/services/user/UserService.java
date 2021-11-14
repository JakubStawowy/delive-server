package com.example.rentiaserver.data.services.user;

import com.example.rentiaserver.data.po.FeedbackPo;
import com.example.rentiaserver.data.po.UserPo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.rentiaserver.constants.ApplicationConstants.Sql.ORDER_BY_CREATED_AT;

@Service
public class UserService {

    private final UserDao userRepository;
    private final FeedbackDao feedbackRepository;

    public UserService(UserDao userRepository, FeedbackDao feedbackRepository) {
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public Optional<UserPo> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<FeedbackPo> getFeedbackPosByUserPoId(Long userId) {
        return feedbackRepository.getFeedbackPosByUserPoId(userId);
    }

    public Iterable<UserPo> findAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(UserPo userPo) {
        userRepository.save(userPo);
    }

    public void saveFeedback(FeedbackPo feedbackPo) {
        feedbackRepository.save(feedbackPo);
    }

    public void deleteFeedbackById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<FeedbackPo> findFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId);
    }

    public Optional<UserPo> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void saveAllUsers(List<UserPo> userPos) {
        userRepository.saveAll(userPos);
    }

}