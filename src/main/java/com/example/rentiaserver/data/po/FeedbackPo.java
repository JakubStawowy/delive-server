package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.data.enums.FeedbackRate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "FEEDBACK")
public class FeedbackPo extends BaseEntityPo {

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackRate rate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private UserPo authorPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserPo userPo;

    public FeedbackPo(String content, FeedbackRate rate, UserPo authorPo, UserPo userPo) {
        this.content = content;
        this.rate = rate;
        this.authorPo = authorPo;
        this.userPo = userPo;
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

    public UserPo getAuthorPo() {
        return authorPo;
    }

    public void setAuthorPo(UserPo authorPo) {
        this.authorPo = authorPo;
    }

    public UserPo getUserPo() {
        return userPo;
    }

    public void setUserPo(UserPo userPo) {
        this.userPo = userPo;
    }
}
