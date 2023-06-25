package com.example.rentiaserver.user.model.po;

import com.example.rentiaserver.base.model.po.BaseEntityPo;
import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.user.model.bc.FeedbackRate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;

@Entity
@Table(name = "TB_FEEDBACK")
public class FeedbackPo extends BaseEntityPo {

    @Column(nullable = false)
    private String content;

//    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackRate rate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private UserPo authorPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserPo userPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ID", nullable = false)
    private OrderPo orderPo;

    public FeedbackPo(String content, FeedbackRate rate, UserPo authorPo, UserPo userPo, OrderPo orderPo) {
        this.content = content;
        this.rate = rate;
        this.authorPo = authorPo;
        this.userPo = userPo;
        this.orderPo = orderPo;
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

    public OrderPo getOrderPo() {
        return orderPo;
    }

    public void setOrderPo(OrderPo orderPo) {
        this.orderPo = orderPo;
    }
}
