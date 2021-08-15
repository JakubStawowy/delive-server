package com.example.rentiaserver.data.controllers.announcement;

import com.example.rentiaserver.data.dao.AnnouncementService;
import com.example.rentiaserver.data.to.DeliveryAnnouncementTo;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.to.NormalAnnouncementTo;
import com.example.rentiaserver.data.helpers.AnnouncementToCreatorHelper;
import com.example.rentiaserver.data.po.DeliveryAnnouncementPo;
import com.example.rentiaserver.data.po.NormalAnnouncementPo;
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

    @GetMapping("/delivery")
    public List<DeliveryAnnouncementTo> getDeliveryAnnouncements() {
        List<DeliveryAnnouncementPo> announcements = announcementService.getAllDeliveryAnnouncements();
        List<DeliveryAnnouncementTo> announcementTransferObjects = new LinkedList<>();
        announcements.forEach(announcement -> announcementTransferObjects.add(AnnouncementToCreatorHelper.create(announcement)));
        return announcementTransferObjects;
    }

    @GetMapping("/normal")
    public List<NormalAnnouncementTo> getNormalAnnouncements() {
        List<NormalAnnouncementPo> announcements = announcementService.getAllNormalAnnouncements();
        List<NormalAnnouncementTo> announcementTransferObjects = new LinkedList<>();
        announcements.forEach(announcement -> announcementTransferObjects.add(AnnouncementToCreatorHelper.create(announcement)));
        return announcementTransferObjects;
    }
}
