package com.example.rentiaserver.delivery.po;

import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "delivery")
public class DeliveryPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserPo user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "announcement_id")
    private AnnouncementPo announcement;

    @JoinColumn(name = "created_at", updatable = false)
    private Date createdAt;

    @JoinColumn(name = "state")
    @Enumerated(EnumType.STRING)
    private DeliveryState deliveryState;

    public DeliveryPo(UserPo user, AnnouncementPo announcement) {
        this.user = user;
        this.announcement = announcement;
    }

    public DeliveryPo() {}

    @PrePersist
    public void init() {
        createdAt = new Date(System.currentTimeMillis());
        deliveryState = DeliveryState.REGISTERED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public DeliveryState getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(DeliveryState commissionState) {
        this.deliveryState = commissionState;
    }
}
