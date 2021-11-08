package com.example.rentiaserver.data.to;

import com.example.rentiaserver.data.api.BaseEntityTo;
import com.example.rentiaserver.data.po.FeedbackPo;

public class FeedbackTo extends BaseEntityTo {

    private final String content;
    private final int rate;
    private final String authorName;
    private final String authorSurname;
    private final Long userId;
    private final Long authorId;

    public FeedbackTo(FeedbackPo feedback) {
        super(feedback.getId(), String.valueOf(feedback.getCreatedAt()));
        content = feedback.getContent();
        rate = feedback.getRate().ordinal();
        authorName = feedback.getAuthorPo().getName();
        authorSurname = feedback.getAuthorPo().getSurname();
        authorId = feedback.getAuthorPo().getId();
        userId = feedback.getUserPo().getId();
    }

    public FeedbackTo(Long id, String createdAt, String content, int rate, String authorName, String authorSurname, Long authorId, Long userId) {
        super(id, createdAt);
        this.content = content;
        this.rate = rate;
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.authorId = authorId;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public int getRate() {
        return rate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getUserId() {
        return userId;
    }
}
