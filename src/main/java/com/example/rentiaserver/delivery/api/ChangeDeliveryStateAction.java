package com.example.rentiaserver.delivery.api;

import com.example.rentiaserver.delivery.dao.DeliveryRepository;
import com.example.rentiaserver.delivery.po.DeliveryPo;

public abstract class ChangeDeliveryStateAction {

    protected abstract void changeState(DeliveryRepository deliveryRepository, DeliveryPo deliveryPo);

    public void startAction(DeliveryRepository deliveryRepository, Long deliveryId) {
        DeliveryPo deliveryPo = deliveryRepository.findById(deliveryId).orElse(null);
        changeState(deliveryRepository, deliveryPo);
    }
}
