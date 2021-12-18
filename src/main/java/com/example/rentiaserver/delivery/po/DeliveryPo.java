package com.example.rentiaserver.delivery.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.data.po.UserPo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_DELIVERY")
public class DeliveryPo extends BaseEntityPo {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    private UserPo userPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ID")
    private OrderPo orderPo;

    @JoinColumn(name = "STARTED_AT", updatable = false)
    private Date startedAt;

    @JoinColumn(name = "FINISHED_AT", updatable = false)
    private Date finishedAt;

    @JoinColumn(name = "STATE")
    @Enumerated(EnumType.STRING)
    private DeliveryState deliveryState;

    public DeliveryPo(UserPo userPo, OrderPo orderPo) {
        this.userPo = userPo;
        this.orderPo = orderPo;
    }

    public DeliveryPo() {}

    @Override
    public void init() {
        super.init();
        deliveryState = DeliveryState.REGISTERED;
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

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public DeliveryState getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(DeliveryState deliveryState) {
        this.deliveryState = deliveryState;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }
}
