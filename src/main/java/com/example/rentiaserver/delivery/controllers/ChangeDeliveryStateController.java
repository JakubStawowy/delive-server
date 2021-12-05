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
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
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

    @PutMapping("/pick")
    public void pickPackage(@RequestParam Long deliveryId) {
        deliveryService.findDeliveryById(deliveryId).ifPresent(deliveryPo -> changeDeliveryState(deliveryPo, DeliveryState.TO_START));
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
    public Set<ActionPair> getNextActionNames(@RequestParam DeliveryState deliveryState, @RequestParam Long announcementAuthorId,
                                        @RequestParam Long delivererId, HttpServletRequest request) {
        final Long loggedUserId = JsonWebTokenHelper.getRequesterId(request);
        final boolean isUserPrincipal = announcementAuthorId.compareTo(loggedUserId) == 0;
        final boolean isUserDeliverer = delivererId.compareTo(loggedUserId) == 0;
        if (DeliveryState.REGISTERED.equals(deliveryState) && isUserDeliverer) {
            return Collections.singleton(new ActionPair("pick", "pick up the load"));
        }
        if (DeliveryState.TO_START.equals(deliveryState) && isUserPrincipal) {
            return Collections.singleton(new ActionPair("start", "hand over package to deliverer"));
        }
        if (DeliveryState.STARTED.equals(deliveryState) && isUserDeliverer) {
            return Collections.singleton(new ActionPair("finish", "package delivered"));
        }
        if (DeliveryState.FINISHED.equals(deliveryState) && isUserPrincipal) {
            return Collections.singleton(new ActionPair("close", "close delivery"));
        }
        if (DeliveryState.TO_ACCEPT.equals(deliveryState) && isUserPrincipal) {
            return new HashSet<>(Arrays.asList(new ActionPair("accept", "package delivered"),
                    new ActionPair("discard", "package not delivered")));
        }
        if (DeliveryState.CLOSED.equals(deliveryState)) {
            return Collections.singleton(new ActionPair("-", "-"));
        }
        return Collections.singleton(new ActionPair("waiting", "waiting"));
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

    private static class ActionPair implements Serializable {

        private String value;
        private String label;

        public ActionPair(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
