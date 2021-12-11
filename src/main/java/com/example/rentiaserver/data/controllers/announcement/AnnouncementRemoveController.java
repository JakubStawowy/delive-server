package com.example.rentiaserver.data.controllers.announcement;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.services.announcement.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(value = AnnouncementRemoveController.BASE_ENDPOINT)
public final class AnnouncementRemoveController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/announcements";
    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementRemoveController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @DeleteMapping(value = "/delete")
    public void removeAnnouncement(@RequestParam Long announcementId) {
        announcementService.deleteById(announcementId);
    }
}
