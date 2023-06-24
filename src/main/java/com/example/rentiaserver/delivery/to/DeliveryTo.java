package com.example.rentiaserver.delivery.to;

import com.example.rentiaserver.data.api.BaseEntityTo;
import com.example.rentiaserver.data.to.OrderTo;

public class DeliveryTo extends BaseEntityTo {

    private final Long delivererId;
    private final OrderTo order;
    private final String deliveryState;

    public DeliveryTo(Long id, String createdAt, Long delivererId, OrderTo order, String deliveryState) {
        super(id, createdAt);
        this.delivererId = delivererId;
        this.order = order;
        this.deliveryState = deliveryState;
    }

    public Long getDelivererId() {
        return delivererId;
    }

    public OrderTo getOrder() {
        return order;
    }

    public String getDeliveryState() {
        return deliveryState;
    }
}
