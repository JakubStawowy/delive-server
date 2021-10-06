package com.example.rentiaserver.delivery.actions;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.api.ChangeDeliveryStateAction;
import com.example.rentiaserver.delivery.dao.DeliveryDao;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.services.DeliveryService;
import com.example.rentiaserver.delivery.services.MessageService;

import java.math.BigDecimal;

public class CloseDeliveryAction extends ChangeDeliveryStateAction {

    @Override
    protected void changeState(DeliveryService deliveryService, MessageService messageService, DeliveryPo deliveryPo) {
        AnnouncementPo announcementPo = deliveryPo.getAnnouncement();
        BigDecimal amount = announcementPo.getAmount();
        UserPo deliverer = deliveryPo.getUser();
        BigDecimal delivererWalletBalance = deliverer
                .getUserWallet()
                .getBalance();
        UserPo principal = announcementPo.getAuthor();
        BigDecimal principalBalance = principal.getUserWallet().getBalance();
        deliverer.getUserWallet().setBalance(delivererWalletBalance.add(amount));
        principal.getUserWallet().setBalance(principalBalance.subtract(amount));
        deliveryService.save(deliverer, principal);
        deliveryPo.setDeliveryState(DeliveryState.CLOSED);
        deliveryService.save(deliveryPo);

    }

    @Override
    protected String getMessage(UserPo receiver) {
        return "Hi " + receiver.getUserDetails().getName() + " delivery closed";
    }
}
