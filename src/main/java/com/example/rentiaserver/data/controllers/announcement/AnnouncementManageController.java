package com.example.rentiaserver.data.controllers.announcement;

import com.example.rentiaserver.data.services.AnnouncementService;
import com.example.rentiaserver.data.services.UserService;
import com.example.rentiaserver.data.to.AnnouncementTo;
import com.example.rentiaserver.data.po.*;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.maps.po.LocationPo;
import com.example.rentiaserver.maps.services.PositionStackReverseGeocodeService;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = AnnouncementManageController.BASE_ENDPOINT)
public class AnnouncementManageController {

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
    @Transactional
    public void saveAnnouncement(@RequestBody AnnouncementTo announcementTo, HttpServletRequest request) {

        userService.findUserById(JsonWebTokenHelper.getRequesterId(request)).ifPresent(author -> {
            try {
                if (announcementTo.getId() != null) {
                    announcementService.getAnnouncementById(announcementTo.getId()).ifPresent(announcementPo -> {
                        try {
                            editAnnouncement(announcementPo, announcementTo);
                        } catch (InterruptedException | ParseException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                else {
                    addAnnouncement(author, announcementTo);
                }
            } catch (InterruptedException | ParseException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void editAnnouncement(AnnouncementPo announcementPo, AnnouncementTo announcementTo) throws InterruptedException, ParseException, IOException {
        JSONObject addressFromJson = geocoderService.getAddressFromCoordinates(
                announcementTo.getDestinationFrom().getLongitude(),
                announcementTo.getDestinationFrom().getLatitude());
        JSONObject addressToJson = geocoderService.getAddressFromCoordinates(
                announcementTo.getDestinationTo().getLongitude(),
                announcementTo.getDestinationTo().getLatitude());

        announcementPo.getInitialLocationPo().setLatitude(announcementTo.getDestinationFrom().getLatitude());
        announcementPo.getInitialLocationPo().setLongitude(announcementTo.getDestinationFrom().getLongitude());
        announcementPo.getInitialLocationPo().setAddress(String.valueOf(addressFromJson.get("name")));
        announcementPo.getInitialLocationPo().setLocality(String.valueOf(addressFromJson.get("locality")));
        announcementPo.getInitialLocationPo().setCountry(String.valueOf(addressFromJson.get("country")));

        announcementPo.getFinalLocationPo().setLatitude(announcementTo.getDestinationTo().getLatitude());
        announcementPo.getFinalLocationPo().setLongitude(announcementTo.getDestinationTo().getLongitude());
        announcementPo.getFinalLocationPo().setAddress(String.valueOf(addressToJson.get("name")));
        announcementPo.getFinalLocationPo().setLocality(String.valueOf(addressToJson.get("locality")));
        announcementPo.getFinalLocationPo().setCountry(String.valueOf(addressToJson.get("country")));

        announcementPo.setRequireTransportWithClient(announcementTo.isRequireTransportWithClient());
        announcementPo.setAmount(new BigDecimal(announcementTo.getAmount()));
        Set<PackagePo> packagePos = new HashSet<>();
        announcementService.deleteAllByAnnouncement(announcementPo);
        announcementTo.getPackages().forEach(packageTo ->
                packagePos.add(new PackagePo(
                        new BigDecimal(packageTo.getPackageLength()),
                        new BigDecimal(packageTo.getPackageWidth()),
                        new BigDecimal(packageTo.getPackageHeight()),
                        announcementPo
                )));
        announcementService.saveAllPackages(packagePos);
    }

    private void addAnnouncement(UserPo author, AnnouncementTo announcementTo) throws InterruptedException, ParseException, IOException {
        JSONObject addressFromJson;
        JSONObject addressToJson;
        if (announcementTo.getDestinationFrom().getLongitude() == null) {
            addressFromJson = geocoderService.getAddressFromData(
                    announcementTo.getDestinationFrom().getAddress(),
                    announcementTo.getDestinationFrom().getLocality(),
                    announcementTo.getDestinationFrom().getCountry());
        }
        else {
            addressFromJson = geocoderService.getAddressFromCoordinates(
                    announcementTo.getDestinationFrom().getLongitude(),
                    announcementTo.getDestinationFrom().getLatitude());
        }
        if (announcementTo.getDestinationTo().getLongitude() == null) {

            addressToJson = geocoderService.getAddressFromData(
                    announcementTo.getDestinationTo().getAddress(),
                    announcementTo.getDestinationTo().getLocality(),
                    announcementTo.getDestinationTo().getCountry());
        }
        else {
            addressToJson = geocoderService.getAddressFromCoordinates(
                    announcementTo.getDestinationTo().getLongitude(),
                    announcementTo.getDestinationTo().getLatitude());
        }

        AnnouncementPo announcement = new AnnouncementPo(
                new LocationPo(
                        Double.valueOf(String.valueOf(addressFromJson.get("latitude"))),
                        Double.valueOf(String.valueOf(addressFromJson.get("longitude"))),
                        String.valueOf(addressFromJson.get("name")),
                        String.valueOf(addressFromJson.get("locality")),
                        String.valueOf(addressFromJson.get("country"))),
                new LocationPo(
                        Double.valueOf(String.valueOf(addressToJson.get("latitude"))),
                        Double.valueOf(String.valueOf(addressToJson.get("longitude"))),
                        String.valueOf(addressToJson.get("name")),
                        String.valueOf(addressToJson.get("locality")),
                        String.valueOf(addressToJson.get("country"))),
                author,
                new BigDecimal(announcementTo.getAmount()),
                announcementTo.isRequireTransportWithClient()

        );
        if (announcementTo.getId() != null) {
            announcement.setId(announcementTo.getId());
        }
        announcementService.save(announcement);
        Set<PackagePo> packagePos = new HashSet<>();
        announcementTo.getPackages().forEach(packageTo ->
                packagePos.add(new PackagePo(
                        new BigDecimal(packageTo.getPackageLength()),
                        new BigDecimal(packageTo.getPackageWidth()),
                        new BigDecimal(packageTo.getPackageHeight()),
                        announcement
                )));
        announcementService.saveAllPackages(packagePos);
    }
}
