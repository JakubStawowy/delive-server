package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.data.enums.FeedbackRate;
import com.example.rentiaserver.constants.TableNamesConstants;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = TableNamesConstants.FEEDBACK_TABLE_NAME)
public class FeedbackPo extends BaseEntityPo {

    @NotEmpty
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FeedbackRate rate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id")
    private UserPo author;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserPo user;

    public FeedbackPo(@NotEmpty String content, @NotNull FeedbackRate rate, UserPo author, UserPo user) {
        this.content = content;
        this.rate = rate;
        this.author = author;
        this.user = user;
    }

    public FeedbackPo() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FeedbackRate getRate() {
        return rate;
    }

    public void setRate(FeedbackRate rate) {
        this.rate = rate;
    }

    public UserPo getAuthor() {
        return author;
    }

    public void setAuthor(UserPo author) {
        this.author = author;
    }

    public UserPo getUser() {
        return user;
    }

    public void setUser(UserPo user) {
        this.user = user;
    }
}
