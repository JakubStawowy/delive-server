package com.example.rentiaserver.delivery.to;

import com.example.rentiaserver.data.to.AnnouncementTo;

import java.io.Serializable;

public class DeliveryTo implements Serializable {

    private final Long deliveryId;
    private final Long delivererId;
    private final String registeredAt;
    private final AnnouncementTo announcement;
    private final String deliveryState;

    public DeliveryTo(Long deliveryId, Long delivererId, String registeredAt, AnnouncementTo announcement, String deliveryState) {
        this.deliveryId = deliveryId;
        this.delivererId = delivererId;
        this.registeredAt = registeredAt;
        this.announcement = announcement;
        this.deliveryState = deliveryState;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public Long getDelivererId() {
        return delivererId;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public AnnouncementTo getAnnouncement() {
        return announcement;
    }

    public String getDeliveryState() {
        return deliveryState;
    }
}
