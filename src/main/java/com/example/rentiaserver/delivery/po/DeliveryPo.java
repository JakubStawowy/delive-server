package com.example.rentiaserver.delivery.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.data.po.FeedbackPo;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DELIVERIES")
public class DeliveryPo extends BaseEntityPo {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    private UserPo userPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ANNOUNCEMENT_ID")
    private AnnouncementPo announcementPo;

    @JoinColumn(name = "FINISHED_AT", updatable = false)
    private Date finishedAt;

    @JoinColumn(name = "STATE")
    @Enumerated(EnumType.STRING)
    private DeliveryState deliveryState;

    public DeliveryPo(UserPo userPo, AnnouncementPo announcementPo) {
        this.userPo = userPo;
        this.announcementPo = announcementPo;
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

    public AnnouncementPo getAnnouncementPo() {
        return announcementPo;
    }

    public void setAnnouncementPo(AnnouncementPo announcementPo) {
        this.announcementPo = announcementPo;
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
}
