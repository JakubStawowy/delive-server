package com.example.rentiaserver.delivery.actions;

import com.example.rentiaserver.delivery.api.ChangeDeliveryStateAction;
import com.example.rentiaserver.delivery.dao.DeliveryRepository;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.delivery.po.DeliveryPo;

public class StartDeliveryAction extends ChangeDeliveryStateAction {

    @Override
    protected void changeState(DeliveryRepository deliveryRepository, DeliveryPo deliveryPo) {
        deliveryPo.setDeliveryState(DeliveryState.STARTED);
        deliveryRepository.save(deliveryPo);
    }
}
