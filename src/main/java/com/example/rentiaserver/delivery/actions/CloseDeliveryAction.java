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
        AnnouncementPo announcementPo = deliveryPo.getAnnouncementPo();
        announcementPo.setArchived(true);
        BigDecimal amount = announcementPo.getAmount();
        UserPo deliverer = deliveryPo.getUserPo();
        BigDecimal delivererWalletBalance = deliverer
                .getUserWalletPo()
                .getBalance();
        UserPo principal = announcementPo.getAuthorPo();
        BigDecimal principalBalance = principal.getUserWalletPo().getBalance();
        deliverer.getUserWalletPo().setBalance(delivererWalletBalance.add(amount));
        principal.getUserWalletPo().setBalance(principalBalance.subtract(amount));
        deliveryService.save(deliverer, principal);
        deliveryPo.setDeliveryState(DeliveryState.CLOSED);
        deliveryService.save(deliveryPo);

    }

    @Override
    protected String getMessage(UserPo receiver) {
        return "Hi " + receiver.getName() + " delivery closed";
    }
}
