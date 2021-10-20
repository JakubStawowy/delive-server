package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.helpers.AnnouncementToCreatorHelper;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.services.DeliveryService;
import com.example.rentiaserver.finance.po.TransferPo;
import com.example.rentiaserver.finance.po.UserWalletPo;
import com.example.rentiaserver.maps.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = ChangeDeliveryStateController.BASE_ENDPOINT)
public class ChangeDeliveryStateController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/delivery";

    private final DeliveryService deliveryService;

    @Autowired
    public ChangeDeliveryStateController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
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
        LocationTo clientLocation = new LocationTo(null, null, clientLatitude, clientLongitude, null, null, null);
        Optional<DeliveryPo> optionalDeliveryPo = deliveryService.findDeliveryById(deliveryId);
        if (optionalDeliveryPo.isPresent()) {
            DeliveryPo deliveryPo = optionalDeliveryPo.get();
            if (deliveryService.getDistance(AnnouncementToCreatorHelper.create(deliveryPo.getAnnouncementPo()).getDestinationTo(),
                        clientLocation) <= radius) {
                completeTransfer(deliveryPo);
            }
            else {
                changeDeliveryState(deliveryPo, DeliveryState.TO_ACCEPT);
            }
        }
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
        });
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
