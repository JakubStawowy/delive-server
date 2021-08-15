package com.example.rentiaserver.data.to;

import com.example.rentiaserver.data.po.FeedbackPo;

import java.io.Serializable;

public class FeedbackTo implements Serializable {

    private final String content;
    private final String createdAt;
    private final int rate;
    private final String authorName;
    private final String authorSurname;
    private final Long authorId;

    public FeedbackTo(FeedbackPo feedback) {
        content = feedback.getContent();
        createdAt = feedback.getCreatedAt().toString();
        rate = feedback.getRate().ordinal();
        authorName = feedback.getAuthor().getUserDetails().getName();
        authorSurname = feedback.getAuthor().getUserDetails().getSurname();
        authorId = feedback.getAuthor().getId();
    }

    public FeedbackTo(String content, String createdAt, int rate, String authorName, String authorSurname, Long authorId) {
        this.content = content;
        this.createdAt = createdAt;
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
