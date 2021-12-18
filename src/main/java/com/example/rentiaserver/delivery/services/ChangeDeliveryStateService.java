package com.example.rentiaserver.delivery.services;

import com.example.rentiaserver.data.helpers.OrderToCreatorHelper;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.api.BaseChangeDeliveryStateService;
import com.example.rentiaserver.delivery.dao.MessageDao;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.delivery.enums.MessageType;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.po.MessagePo;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public final class ChangeDeliveryStateService extends BaseChangeDeliveryStateService {

    private final MessageDao messageDao;
    private static final double RADIUS = 0.5;

    @Autowired
    public ChangeDeliveryStateService(DeliveryService deliveryService, MessageDao messageDao) {
        super(deliveryService);
        this.messageDao = messageDao;
    }

    @Override
    public void finishDelivery(DeliveryPo deliveryPo, LocationTo clientLocation) {

        if (clientLocation.getLatitude() == 0 && clientLocation.getLongitude() == 0) {
            changeDeliveryState(deliveryPo, DeliveryState.TO_ACCEPT);
        }
        else {
            double distance = deliveryService.getDistance(OrderToCreatorHelper.create(deliveryPo.getAnnouncementPo()).getDestinationTo(),
                    clientLocation);
            if (distance <= RADIUS) {
                completeTransfer(deliveryPo);
            }
            else {
                changeDeliveryState(deliveryPo, DeliveryState.TO_ACCEPT);
            }
        }
    }

    @Override
    public void closeDelivery(DeliveryPo deliveryPo) {
        AnnouncementPo announcementPo = deliveryPo.getAnnouncementPo();
        announcementPo.setArchived(true);
        deliveryPo.setDeliveryState(DeliveryState.CLOSED);
        deliveryService.save(deliveryPo);

        String message = "Congratulations! Delivery closed";
        messageDao.saveAll(Arrays.asList(
                new MessagePo(
                        message,
                        announcementPo,
                        announcementPo.getAuthorPo(),
                        deliveryPo.getUserPo(),
                        MessageType.INFO),
                new MessagePo(
                        message,
                        announcementPo,
                        deliveryPo.getUserPo(),
                        announcementPo.getAuthorPo(),
                        MessageType.INFO)));
    }

    @Override
    public void startDelivery(DeliveryPo deliveryPo) {
        AnnouncementPo announcementPo = deliveryPo.getAnnouncementPo();
        BigDecimal amount = announcementPo.getAmount();
        UserPo principal = announcementPo.getAuthorPo();
        BigDecimal principalBalance = principal.getBalance();
        principal.setBalance(principalBalance.subtract(amount));
        changeDeliveryState(deliveryPo, DeliveryState.STARTED);
    }
}