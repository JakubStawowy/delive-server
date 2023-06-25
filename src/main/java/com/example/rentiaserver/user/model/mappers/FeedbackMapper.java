package com.example.rentiaserver.user.model.mappers;

import com.example.rentiaserver.user.model.po.FeedbackPo;
import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.user.model.to.FeedbackTo;

public class FeedbackMapper {

    public static FeedbackTo mapFeedbackPoToTo(FeedbackPo feedbackPo) {
        UserPo authorPo = feedbackPo.getAuthorPo();
        return FeedbackTo.builder()
                .id(feedbackPo.getId())
                .createdAt(feedbackPo.getCreatedAt())
                .content(feedbackPo.getContent())
                .rate(feedbackPo.getRate().getValue())
                .authorName(authorPo.getName())
                .authorId(authorPo.getId())
                .userId(feedbackPo.getUserPo().getId())
                .build();
    }
}
