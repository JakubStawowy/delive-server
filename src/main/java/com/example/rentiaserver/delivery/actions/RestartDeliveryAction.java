package com.example.rentiaserver.delivery.actions;

import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.api.ChangeDeliveryStateAction;
import com.example.rentiaserver.delivery.dao.DeliveryDao;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.services.DeliveryService;
import com.example.rentiaserver.delivery.services.MessageService;

public class RestartDeliveryAction extends ChangeDeliveryStateAction {
    @Override
    protected void changeState(DeliveryService deliveryService, MessageService messageService, DeliveryPo deliveryPo) {

    }

    @Override
    protected String getMessage(UserPo receiver) {
        return null;
    }
}
