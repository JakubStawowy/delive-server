package com.example.rentiaserver.delivery.api;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.dao.DeliveryDao;
import com.example.rentiaserver.delivery.enums.MessageType;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.services.DeliveryService;
import com.example.rentiaserver.delivery.services.MessageService;

import java.util.Optional;

public abstract class ChangeDeliveryStateAction {

    protected abstract void changeState(DeliveryService deliveryService, MessageService messageService, DeliveryPo deliveryPo);
    protected abstract String getMessage(UserPo receiver);

    public void startAction(DeliveryService deliveryService, MessageService messageService, Long deliveryId) {
        Optional<DeliveryPo> optionalDeliveryPo = deliveryService.findDeliveryById(deliveryId);
        optionalDeliveryPo.ifPresent(deliveryPo -> startAction(deliveryPo, deliveryService, messageService));
    }

    private void startAction(DeliveryPo deliveryPo, DeliveryService deliveryService, MessageService messageService) {
        changeState(deliveryService, messageService, deliveryPo);
        AnnouncementPo announcementPo = deliveryPo.getAnnouncement();
        announcementPo.getAssociatedUsers().forEach(userPo -> messageService.saveMessage(getMessage(deliveryPo.getUser()), announcementPo, deliveryPo.getUser(), userPo, MessageType.INFO));
    }
}
