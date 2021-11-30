package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.helpers.AnnouncementToCreatorHelper;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.dao.MessageDao;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.delivery.enums.MessageType;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.po.MessagePo;
import com.example.rentiaserver.delivery.services.DeliveryService;
import com.example.rentiaserver.finance.po.TransferPo;
import com.example.rentiaserver.finance.po.UserWalletPo;
import com.example.rentiaserver.geolocation.to.LocationTo;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = ChangeDeliveryStateController.BASE_ENDPOINT)
public class ChangeDeliveryStateController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/delivery";

    private final DeliveryService deliveryService;
    private final MessageDao messageDao;

    @Autowired
    public ChangeDeliveryStateController(DeliveryService deliveryService, MessageDao messageDao) {
        this.deliveryService = deliveryService;
        this.messageDao = messageDao;
    }

    @PutMapping("/start")
    public void startDelivery(@RequestParam Long deliveryId) {
        deliveryService.findDeliveryById(deliveryId).ifPresent(deliveryPo -> {

            AnnouncementPo announcementPo = deliveryPo.getAnnouncementPo();
            BigDecimal amount = announcementPo.getAmount();
            UserWalletPo principalWallet = announcementPo.getAuthorPo().getUserWalletPo();
            BigDecimal principalBalance = principalWallet.getBalance();
            principalWallet.setBalance(principalBalance.subtract(amount));

            deliveryService.save(new TransferPo(
                    announcementPo.getAuthorPo().getUserWalletPo(),
                    deliveryPo.getUserPo().getUserWalletPo(),
                    amount,
                    "EUR"
            ));
            changeDeliveryState(deliveryPo, DeliveryState.STARTED);
        });
    }

    @PutMapping("/finish")
    public void finishDelivery(@RequestParam Long deliveryId, @RequestParam double clientLatitude, @RequestParam double clientLongitude) {

        final double radius = 2;

        LocationTo clientLocation = new LocationTo(null, null, clientLatitude, clientLongitude, null);
        Optional<DeliveryPo> optionalDeliveryPo = deliveryService.findDeliveryById(deliveryId);
        optionalDeliveryPo.ifPresent(deliveryPo -> {

            if (clientLatitude == 0 && clientLongitude == 0) {
                changeDeliveryState(deliveryPo, DeliveryState.TO_ACCEPT);
            }
            else {
                double distance = deliveryService.getDistance(AnnouncementToCreatorHelper.create(deliveryPo.getAnnouncementPo()).getDestinationTo(),
                        clientLocation);
                if (distance <= radius) {
                    completeTransfer(deliveryPo);
                }
                else {
                    changeDeliveryState(deliveryPo, DeliveryState.TO_ACCEPT);
                }
            }
        });
    }

    @PutMapping("/accept")
    public void acceptDelivery(@RequestParam Long deliveryId) {
        deliveryService.findDeliveryById(deliveryId).ifPresent(this::completeTransfer);
    }

    @PutMapping("/discard")
    public void discardDelivery(@RequestParam Long deliveryId) {
        deliveryService.findDeliveryById(deliveryId).ifPresent(deliveryPo -> changeDeliveryState(deliveryPo, DeliveryState.STARTED));
    }

    @PutMapping("/close")
    public void closeDelivery(@RequestParam Long deliveryId) {
        deliveryService.findDeliveryById(deliveryId).ifPresent(deliveryPo -> {
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
        });
    }

    @GetMapping("/actionName")
    public Set<String> getNextActionNames(@RequestParam DeliveryState deliveryState, @RequestParam Long announcementAuthorId,
                                    @RequestParam Long delivererId, HttpServletRequest request) {
        final Long loggedUserId = JsonWebTokenHelper.getRequesterId(request);
        final boolean isUserPrincipal = announcementAuthorId.compareTo(loggedUserId) == 0;
        final boolean isUserDeliverer = delivererId.compareTo(loggedUserId) == 0;
        if (DeliveryState.REGISTERED.equals(deliveryState) && isUserDeliverer) {
            return Collections.singleton("start");
        }
        if (DeliveryState.STARTED.equals(deliveryState) && isUserDeliverer) {
            return Collections.singleton("finish");
        }
        if (DeliveryState.FINISHED.equals(deliveryState) && isUserPrincipal) {
            return Collections.singleton("close");
        }
        if (DeliveryState.TO_ACCEPT.equals(deliveryState) && isUserPrincipal) {
            return new HashSet<>(Arrays.asList("accept", "discard"));
        }
        if (DeliveryState.CLOSED.equals(deliveryState)) {
            return Collections.singleton("-");
        }
        return Collections.singleton("waiting");
    }

    private void completeTransfer(DeliveryPo deliveryPo) {
        AnnouncementPo announcementPo = deliveryPo.getAnnouncementPo();
        BigDecimal amount = announcementPo.getAmount();
        UserPo deliverer = deliveryPo.getUserPo();
        BigDecimal delivererWalletBalance = deliverer
                .getUserWalletPo()
                .getBalance();
        deliverer.getUserWalletPo().setBalance(delivererWalletBalance.add(amount));
        deliveryService.save(deliverer);
        changeDeliveryState(deliveryPo, DeliveryState.FINISHED);
    }

    private void changeDeliveryState(DeliveryPo deliveryPo, DeliveryState deliveryState) {
        deliveryPo.setDeliveryState(deliveryState);
        deliveryService.save(deliveryPo);
    }
}
