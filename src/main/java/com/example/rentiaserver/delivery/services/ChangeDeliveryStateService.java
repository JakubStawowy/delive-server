package com.example.rentiaserver.delivery.services;

import com.example.rentiaserver.data.helpers.OrderToCreatorHelper;
import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.api.BaseChangeDeliveryStateService;
import com.example.rentiaserver.delivery.dao.MessageDao;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.delivery.enums.MessageType;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.po.MessagePo;
import com.example.rentiaserver.geolocation.to.LocationTo;
import com.example.rentiaserver.security.to.ResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

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
    public ResponseTo finishDelivery(DeliveryPo deliveryPo, LocationTo clientLocation) {

        String message = "Deliverer has reached the destination but system cannot verify this." +
                " You can accept it or discard from the delivery panel.";
        changeDeliveryState(deliveryPo, DeliveryState.TO_ACCEPT);
        if (clientLocation.getLatitude() == 0 && clientLocation.getLongitude() == 0) {
            return new ResponseTo(true, message, HttpStatus.OK);
        }
        else {
            double distance = deliveryService.getDistance(OrderToCreatorHelper.create(deliveryPo.getOrderPo()).getDestinationTo(),
                    clientLocation);
            if (distance <= RADIUS) {
                return new ResponseTo(true, "Deliverer has reached the destination safely! " +
                        "You must accept the delivery from the delivery panel", HttpStatus.OK);
            }
            else {
                return new ResponseTo(true, message, HttpStatus.OK);
            }
        }
    }

    @Override
    public void closeDelivery(DeliveryPo deliveryPo) {
        OrderPo orderPo = deliveryPo.getOrderPo();
        orderPo.setArchived(true);
        deliveryPo.setDeliveryState(DeliveryState.CLOSED);
        deliveryService.save(deliveryPo);

        String message = "Congratulations! Delivery closed";
        messageDao.saveAll(Arrays.asList(
                new MessagePo(
                        message,
                        orderPo,
                        orderPo.getAuthorPo(),
                        deliveryPo.getUserPo(),
                        MessageType.INFO),
                new MessagePo(
                        message,
                        orderPo,
                        deliveryPo.getUserPo(),
                        orderPo.getAuthorPo(),
                        MessageType.INFO)));
    }

    @Override
    public void startDelivery(DeliveryPo deliveryPo) {
        OrderPo orderPo = deliveryPo.getOrderPo();
        BigDecimal salary = orderPo.getSalary();
        UserPo principal = orderPo.getAuthorPo();
        BigDecimal principalBalance = principal.getBalance();
        principal.setBalance(principalBalance.subtract(salary));
        deliveryPo.setStartedAt(new Date(System.currentTimeMillis()));
        changeDeliveryState(deliveryPo, DeliveryState.STARTED);
    }

    @Override
    public void acceptDeliveryFinishRequest(DeliveryPo deliveryPo) {
        deliveryPo.setFinishedAt(new Date(System.currentTimeMillis()));
        completeTransfer(deliveryPo);
    }
}