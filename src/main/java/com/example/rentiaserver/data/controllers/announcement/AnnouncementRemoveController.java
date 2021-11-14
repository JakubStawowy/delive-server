package com.example.rentiaserver.data.controllers.announcement;

import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.services.announcement.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = AnnouncementRemoveController.BASE_ENDPOINT)
public final class AnnouncementRemoveController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/announcements";
    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementRemoveController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @DeleteMapping(value = EndpointConstants.REMOVE_ANNOUNCEMENT_ENDPOINT)
    public void removeAnnouncement(@RequestParam Long announcementId) {
        announcementService.deleteById(announcementId);
    }
}
