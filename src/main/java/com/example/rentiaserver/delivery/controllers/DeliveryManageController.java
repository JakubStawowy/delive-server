package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.delivery.dao.DeliveryRepository;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.dao.AnnouncementService;
import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = DeliveryManageController.BASE_ENDPOINT)
public class DeliveryManageController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/delivery";

    private final AnnouncementService announcementService;
    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;

    @Autowired
    public DeliveryManageController(AnnouncementService announcementService, DeliveryRepository commissionRepository, UserRepository userRepository) {
        this.announcementService = announcementService;
        this.deliveryRepository = commissionRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerDelivery(@RequestParam Long userId, @RequestParam Long announcementId) {
        Optional<UserPo> optionalUser = userRepository.findById(userId);
        Optional<AnnouncementPo> optionalAnnouncement = announcementService.getAnnouncementById(announcementId);
        if (optionalUser.isPresent() && optionalAnnouncement.isPresent()) {
            deliveryRepository.save(new DeliveryPo(
                    optionalUser.get(),
                    optionalAnnouncement.get()));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/change")
    public void changeDeliveryState(@RequestParam String actionName, @RequestParam Long deliveryId) {
        DeliveryState state = DeliveryState.getNextStateAfterAction(actionName);
        state.getAction().startAction(deliveryRepository, deliveryId);
    }
}