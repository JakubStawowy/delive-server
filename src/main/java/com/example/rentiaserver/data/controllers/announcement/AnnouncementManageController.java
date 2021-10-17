package com.example.rentiaserver.data.controllers.announcement;

import com.example.rentiaserver.data.services.AnnouncementService;
import com.example.rentiaserver.data.services.UserService;
import com.example.rentiaserver.data.to.AnnouncementTo;
import com.example.rentiaserver.data.po.*;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.maps.po.LocationPo;
import com.example.rentiaserver.maps.services.PositionStackReverseGeocodeService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = AnnouncementManageController.BASE_ENDPOINT)
public final class AnnouncementManageController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/announcements";
    private final UserService userService;
    private final AnnouncementService announcementService;
    private final PositionStackReverseGeocodeService geocoderService;

    @Autowired
    public AnnouncementManageController(UserService userService, AnnouncementService announcementService, PositionStackReverseGeocodeService geocoderService) {
        this.userService = userService;
        this.announcementService = announcementService;
        this.geocoderService = geocoderService;
    }

    @PostMapping(value = EndpointConstants.ADD_NORMAL_ANNOUNCEMENTS_ENDPOINT)
    public void addNormalAnnouncement(@RequestBody AnnouncementTo announcementTo) {
        userService.findUserById(announcementTo.getAuthorId()).ifPresent(author -> {
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
                new BigDecimal(announcementTo.getAmount()),
                announcementTo.isRequireTransportWithClient()

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
        announcementService.saveAllPackages(packagePos);
    }
}
