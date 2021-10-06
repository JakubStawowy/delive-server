package com.example.rentiaserver.delivery.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "delivery")
public class DeliveryPo extends BaseEntityPo {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserPo user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "announcement_id")
    private AnnouncementPo announcement;

    @JoinColumn(name = "finished_at", updatable = false)
    private Date finishedAt;

    @JoinColumn(name = "state")
    @Enumerated(EnumType.STRING)
    private DeliveryState deliveryState;

    public DeliveryPo(UserPo user, AnnouncementPo announcement) {
        this.user = user;
        this.announcement = announcement;
    }

    public DeliveryPo() {}

    @Override
    public void init() {
        super.init();
        deliveryState = DeliveryState.REGISTERED;
    }

    public UserPo getUser() {
        return user;
    }

    public void setUser(UserPo user) {
        this.user = user;
    }

    public AnnouncementPo getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(AnnouncementPo announcement) {
        this.announcement = announcement;
    }

    public DeliveryState getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(DeliveryState commissionState) {
        this.deliveryState = commissionState;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }
}
