package com.example.rentiaserver.delivery.actions;

import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.api.ChangeDeliveryStateAction;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.services.DeliveryService;
import com.example.rentiaserver.delivery.services.MessageService;

public class FinishDeliveryAction extends ChangeDeliveryStateAction {
    @Override
    protected void changeState(DeliveryService deliveryService, MessageService messageService, DeliveryPo deliveryPo) {

        deliveryPo.setDeliveryState(DeliveryState.FINISHED);
        deliveryService.save(deliveryPo);
    }

    @Override
    protected String getMessage(UserPo receiver) {
        return "Hi " + receiver.getName() + "! Your delivery is finished";
    }
}
