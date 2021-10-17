package com.example.rentiaserver.data.controllers.announcement;

import com.example.rentiaserver.data.services.AnnouncementService;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.to.*;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.helpers.AnnouncementToCreatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = AnnouncementLoadController.BASE_ENDPOINT)
public final class AnnouncementLoadController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/announcements";

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementLoadController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("/all")
    public List<AnnouncementTo> getAllAnnouncements() {
        List<AnnouncementTo> announcementTransferObjects = new LinkedList<>();
        announcementService.getAllAnnouncements()
                .forEach(announcement -> announcementTransferObjects.add(AnnouncementToCreatorHelper.create(announcement)));
        return announcementTransferObjects;
    }

    @GetMapping("/announcement")
    public AnnouncementTo getAnnouncementById(@RequestParam Long announcementId) {
        return announcementService.getAnnouncementById(announcementId).map(AnnouncementToCreatorHelper::create).orElse(null);
    }
}
