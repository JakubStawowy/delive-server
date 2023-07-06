package com.example.rentiaserver.delivery.service;

import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.order.service.OrderService;
import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.user.api.IUserService;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.delivery.repository.DeliveryRepository;
import com.example.rentiaserver.delivery.api.DeliveryState;
import com.example.rentiaserver.delivery.api.MessageType;
import com.example.rentiaserver.delivery.model.mapper.DeliveryMapper;
import com.example.rentiaserver.delivery.model.po.DeliveryPo;
import com.example.rentiaserver.delivery.model.po.MessagePo;
import com.example.rentiaserver.delivery.api.IMessageService;
import com.example.rentiaserver.delivery.model.to.DeliveryTo;
import com.example.rentiaserver.geolocation.api.IGeolocationService;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryDao;

    private final IMessageService messageService;

    private final IGeolocationService geolocationService;
    
    private final IUserService userService;

    private final OrderService orderService;

    @Autowired
    public DeliveryService(
            DeliveryRepository deliveryDao,
            IMessageService messageService,
            IGeolocationService geolocationService,
            IUserService userService,
            OrderService orderService) {
        this.deliveryDao = deliveryDao;
        this.messageService = messageService;
        this.geolocationService = geolocationService;
        this.userService = userService;
        this.orderService = orderService;
    }

    public Optional<DeliveryPo> findDeliveryByOrderPoAndUserPo(OrderPo orderPo, UserPo userPo) {
        return deliveryDao.findByOrderPoAndUserPo(orderPo, userPo);
    }

    public DeliveryPo getById(Long deliveryId) throws EntityNotFoundException {
        return deliveryDao.findById(deliveryId)
                .orElseThrow(() -> new EntityNotFoundException(DeliveryPo.class, deliveryId));
    }

    public void pickPackage(Long deliveryId) throws EntityNotFoundException {
        DeliveryPo delivery = getById(deliveryId);
        saveDeliveryState(delivery, DeliveryState.TO_START);
    }

    public void startDelivery(Long deliveryId) throws EntityNotFoundException {
        // Start delivery
        DeliveryPo delivery = getById(deliveryId);
        delivery.setStartedAt(new Date(System.currentTimeMillis()));
        saveDeliveryState(delivery, DeliveryState.STARTED);

        // Withdraw funds from the principal's wallet
        OrderPo order = delivery.getOrderPo();
        BigDecimal salary = order.getSalary();
        UserPo principal = order.getAuthorPo();

        BigDecimal principalBalance = principal.getBalance();
        principal.setBalance(principalBalance.subtract(salary));
        userService.save(principal);
    }

    public void finishDelivery(Long deliveryId, LocationTo clientLocation) throws EntityNotFoundException {
        DeliveryPo delivery = getById(deliveryId);
        saveDeliveryState(delivery, DeliveryState.TO_ACCEPT);

        OrderPo order = delivery.getOrderPo();
        
        boolean clientInRequiredDestinationArea = geolocationService.isClientInRequiredDestinationArea(
                order.getId(),
                clientLocation.getLatitude(),
                clientLocation.getLongitude());

        StringBuilder message = new StringBuilder("Deliverer has finished the order.");

        if (!clientInRequiredDestinationArea) {
            message.append(" ")
                    .append("Warning - Deliverer is far from the destination. Make sure the package have been delivered successfully");
        }

        saveInfoMessage(message.toString(), order.getAuthorPo(), order);
    }

    public void acceptDelivery(Long deliveryId) throws EntityNotFoundException {
        DeliveryPo delivery = getById(deliveryId);

        // Set delivery finished
        delivery.setFinishedAt(new Date(System.currentTimeMillis()));
        saveDeliveryState(delivery, DeliveryState.FINISHED);

        UserPo deliverer = delivery.getUserPo();
        BigDecimal delivererWalletBalance = deliverer.getBalance();
        BigDecimal salary = delivery.getOrderPo()
                .getSalary();
        
        deliverer.setBalance(delivererWalletBalance.add(salary));

        userService.save(deliverer);
        
    }

    public void discardDelivery(Long deliveryId) throws EntityNotFoundException {
        DeliveryPo delivery = getById(deliveryId);
        saveDeliveryState(delivery, DeliveryState.STARTED);
    }

    public void closeDelivery(Long deliveryId) throws EntityNotFoundException {

        // Close delivery
        DeliveryPo delivery = getById(deliveryId);
        saveDeliveryState(delivery, DeliveryState.CLOSED);

        // Archive order
        OrderPo order = delivery.getOrderPo();
        orderService.archive(order);

        // Send messages
        String message = "Congratulations! Delivery closed";

        // Send message to principal
        saveInfoMessage(message, order.getAuthorPo(), order);

        // Send message to contractor
        saveInfoMessage(message, delivery.getUserPo(), order);

    }

    public void save(DeliveryPo delivery) {
        deliveryDao.save(delivery);
    }

    public List<DeliveryTo> getAllDelivererDeliveryTos(Long userId) {
        return deliveryDao.findAllByDeliverer(userId)
                .stream()
                .map(DeliveryMapper::mapDeliveryPoToTo)
                .collect(Collectors.toList());
    }

    public List<DeliveryTo> getAllPrincipalDeliveryTos(Long userId) {
        return deliveryDao.findAllByPrincipal(userId)
                .stream()
                .map(DeliveryMapper::mapDeliveryPoToTo)
                .collect(Collectors.toList());
    }

    private void saveInfoMessage(String message, UserPo receiver, OrderPo order) {
        MessagePo toContractorMessage = new MessagePo();

        toContractorMessage.setMessage(message);
        toContractorMessage.setOrderPo(order);
        toContractorMessage.setReceiverPo(receiver);
        toContractorMessage.setMessageType(MessageType.INFO);

        messageService.save(toContractorMessage);
    }

    private void saveDeliveryState(DeliveryPo deliveryPo, DeliveryState deliveryState) {
        deliveryPo.setDeliveryState(deliveryState);
        deliveryDao.save(deliveryPo);
    }

    public Set<NextActionBuilder.ActionPack> getNextActions(
            DeliveryState deliveryState,
            Long orderAuthorId,
            Long delivererId,
            Long loggedUserId) {

        final boolean isUserPrincipal = orderAuthorId.compareTo(loggedUserId) == 0;
        final boolean isUserDeliverer = delivererId.compareTo(loggedUserId) == 0;
        return NextActionBuilder.buildNextAction(deliveryState, isUserPrincipal, isUserDeliverer);
    }
}
