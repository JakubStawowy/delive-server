package com.example.rentiaserver.data.to;

import com.example.rentiaserver.data.api.BaseEntityTo;
import com.example.rentiaserver.data.po.FeedbackPo;

public class FeedbackTo extends BaseEntityTo {

    private final String content;
    private final String createdAt;
    private final int rate;
    private final String authorName;
    private final String authorSurname;
    private final Long authorId;

    public FeedbackTo(FeedbackPo feedback) {
        super(feedback.getId(), String.valueOf(feedback.getCreatedAt()));
        content = feedback.getContent();
        createdAt = String.valueOf(feedback.getCreatedAt());
        rate = feedback.getRate().ordinal();
        authorName = feedback.getAuthorPo().getName();
        authorSurname = feedback.getAuthorPo().getSurname();
        authorId = feedback.getAuthorPo().getId();
    }

    public FeedbackTo(Long id, String createdAt, String content, String createdAt1, int rate, String authorName, String authorSurname, Long authorId) {
        super(id, createdAt);
        this.content = content;
        this.createdAt = createdAt1;
        this.rate = rate;
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
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
}
