package com.example.rentiaserver.delivery.to;

import com.example.rentiaserver.data.api.BaseEntityTo;
import com.example.rentiaserver.data.to.AnnouncementTo;

import java.io.Serializable;

public class DeliveryTo extends BaseEntityTo {

    private final Long delivererId;
    private final AnnouncementTo announcement;
    private final String deliveryState;

    public DeliveryTo(Long id, String createdAt, Long delivererId, AnnouncementTo announcement, String deliveryState) {
        super(id, createdAt);
        this.delivererId = delivererId;
        this.announcement = announcement;
        this.deliveryState = deliveryState;
    }

    public Long getDelivererId() {
        return delivererId;
    }

    public AnnouncementTo getAnnouncement() {
        return announcement;
    }

    public String getDeliveryState() {
        return deliveryState;
    }
}
