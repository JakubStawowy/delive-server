package com.example.rentiaserver.delivery.api;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.services.DeliveryService;

import java.math.BigDecimal;

public abstract class BaseChangeDeliveryStateService implements IChangeDeliveryStateService {

    protected final DeliveryService deliveryService;

    public BaseChangeDeliveryStateService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Override
    public void changeDeliveryState(DeliveryPo deliveryPo, DeliveryState deliveryState) {
        deliveryPo.setDeliveryState(deliveryState);
        deliveryService.save(deliveryPo);
    }

    @Override
    public void completeTransfer(DeliveryPo deliveryPo) {
        AnnouncementPo announcementPo = deliveryPo.getAnnouncementPo();
        BigDecimal amount = announcementPo.getAmount();
        UserPo deliverer = deliveryPo.getUserPo();
        BigDecimal delivererWalletBalance = deliverer.getBalance();
        deliverer.setBalance(delivererWalletBalance.add(amount));
        deliveryService.save(deliverer);
        changeDeliveryState(deliveryPo, DeliveryState.FINISHED);
    }
}
