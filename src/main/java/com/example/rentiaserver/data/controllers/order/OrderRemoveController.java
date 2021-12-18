package com.example.rentiaserver.data.controllers.order;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.services.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(value = OrderRemoveController.BASE_ENDPOINT)
public final class OrderRemoveController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/announcements";
    private final OrderService announcementService;

    @Autowired
    public OrderRemoveController(OrderService announcementService) {
        this.announcementService = announcementService;
    }

    @DeleteMapping(value = "/delete")
    public void removeAnnouncement(@RequestParam Long announcementId) {
        announcementService.deleteById(announcementId);
    }
}
