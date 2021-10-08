package com.example.rentiaserver.data.controllers.announcement;

import com.example.rentiaserver.data.dao.AnnouncementService;
import com.example.rentiaserver.data.dao.PackageRepository;
import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.enums.AnnouncementType;
import com.example.rentiaserver.data.to.AnnouncementTo;
import com.example.rentiaserver.data.po.*;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.delivery.dao.DeliveryDao;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.maps.services.PositionStackReverseGeocodeService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = AnnouncementManageController.BASE_ENDPOINT)
public final class AnnouncementManageController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/announcements";
    private final UserRepository userRepository;
    private final AnnouncementService announcementService;
    private final PackageRepository packageRepository;
    private final PositionStackReverseGeocodeService geocoderService;

    @Autowired
    public AnnouncementManageController(UserRepository userRepository, AnnouncementService announcementService,
                                        PackageRepository packageRepository, PositionStackReverseGeocodeService geocoderService
    ) {
        this.userRepository = userRepository;
        this.announcementService = announcementService;
        this.packageRepository = packageRepository;
        this.geocoderService = geocoderService;
    }

    @PostMapping(value = EndpointConstants.ADD_NORMAL_ANNOUNCEMENTS_ENDPOINT)
    public void addNormalAnnouncement(@RequestBody AnnouncementTo announcementTo) {
        userRepository.findById(announcementTo.getAuthorId()).ifPresent(author -> {
            try {
                saveAnnouncementWithPackages(author, announcementTo);
            } catch (InterruptedException | ParseException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    @PutMapping(value = EndpointConstants.EDIT_ANNOUNCEMENT_ENDPOINT)
    public void editAnnouncement(@RequestBody AnnouncementTo announcementTransferObject, @PathVariable("id") Long id) {
        // TODO
    }

    private void saveAnnouncementWithPackages(UserPo author, AnnouncementTo announcementTo) throws InterruptedException, ParseException, IOException {
        JSONObject addressFromJson = geocoderService.getAddressFromCoordinates(
                announcementTo.getDestinationFrom().getLongitude(),
                announcementTo.getDestinationFrom().getLatitude());
        JSONObject addressToJson = geocoderService.getAddressFromCoordinates(
                announcementTo.getDestinationTo().getLongitude(),
                announcementTo.getDestinationTo().getLatitude());

        AnnouncementPo announcement = new AnnouncementPo(
                new LocationPo(
                        announcementTo.getDestinationFrom().getLatitude(),
                        announcementTo.getDestinationFrom().getLongitude(),
                        String.valueOf(addressFromJson.get("name")),
                        String.valueOf(addressFromJson.get("locality")),
                        String.valueOf(addressFromJson.get("country"))),
                new LocationPo(
                        announcementTo.getDestinationTo().getLatitude(),
                        announcementTo.getDestinationTo().getLongitude(),
                        String.valueOf(addressToJson.get("name")),
                        String.valueOf(addressToJson.get("locality")),
                        String.valueOf(addressToJson.get("country"))),
                author,
                AnnouncementType.NORMAL,
                new BigDecimal(announcementTo.getAmount())
        );
        announcementService.save(announcement);
        Set<PackagePo> packagePos = new HashSet<>();
        announcementTo.getPackages().forEach(packageTo ->
                packagePos.add(new AnnouncementPackagePo(
                        new BigDecimal(packageTo.getPackageLength()),
                        new BigDecimal(packageTo.getPackageWidth()),
                        new BigDecimal(packageTo.getPackageHeight()),
                        announcement
                )));
        packageRepository.saveAll(packagePos);
    }

//    private void saveAnnouncementWithDelivery(UserPo author, AnnouncementTo announcementTo) {
//        DeliveryAnnouncementPo announcementPo = new DeliveryAnnouncementPo(
//                new LocationPo(announcementTo.getDestinationFrom().getLatitude(), announcementTo.getDestinationFrom().getLongitude(), null, null, null),
//                new LocationPo(announcementTo.getDestinationTo().getLatitude(), announcementTo.getDestinationTo().getLongitude(), null, null, null),
//                author,
//                LocalDateTime.parse(announcementTo.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
//        );
//        announcementService.save(announcementPo);
//        deliveryRepository.save(new DeliveryPo(author, announcementPo));
//    }
}
