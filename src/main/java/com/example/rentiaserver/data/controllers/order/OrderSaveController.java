package com.example.rentiaserver.data.controllers.order;

import com.example.rentiaserver.data.services.order.OrderService;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.data.to.AnnouncementTo;
import com.example.rentiaserver.data.po.*;
import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.geolocation.converter.*;
import com.example.rentiaserver.geolocation.helpers.UnwrapResponseHelper;
import com.example.rentiaserver.geolocation.po.LocationPo;
import com.example.rentiaserver.geolocation.to.LocationTo;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import com.example.rentiaserver.security.to.ResponseTo;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(value = OrderSaveController.BASE_ENDPOINT)
@PropertySource("classpath:application.properties")
public class OrderSaveController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/announcements";

    private final UserService userService;
    private final OrderService announcementService;
    private final IGeocodingService forwardGeocodingService;
    private final IGeocodingService reverseGeocodingService;
    private final String emergencyGeocodingMode;

    @Autowired
    public OrderSaveController(UserService userService, OrderService announcementService,
                               ForwardGeocodingService forwardGeocodingService,
                               ReverseGeocodingService reverseGeocodingService,
                               ForwardEmergencyGeocodingService forwardEmergencyGeocodingService,
                               ReverseEmergencyGeocodingService reverseEmergencyGeocodingService,
                               @Value("${geocoding.mode.emergency}") String emergencyGeocodingMode) {
        this.userService = userService;
        this.announcementService = announcementService;
        this.emergencyGeocodingMode = emergencyGeocodingMode;
        if (Boolean.TRUE.equals(Boolean.valueOf(emergencyGeocodingMode))) {
            this.forwardGeocodingService = forwardEmergencyGeocodingService;
            this.reverseGeocodingService = reverseEmergencyGeocodingService;
        } else {
            this.forwardGeocodingService = forwardGeocodingService;
            this.reverseGeocodingService = reverseGeocodingService;
        }
    }

    @PostMapping(value = "/normal/add")
    @Transactional
    public ResponseTo saveAnnouncement(@RequestBody AnnouncementTo announcementTo, HttpServletRequest request) {
        Optional<UserPo> optionalUserPo = userService.findUserById(JsonWebTokenHelper.getRequesterId(request));
        if (optionalUserPo.isPresent()) {
            UserPo author = optionalUserPo.get();
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
                return new ResponseTo(true, null, HttpStatus.OK);
            } catch (InterruptedException | ParseException | IOException e) {
                throw new RuntimeException(e);
            } catch (LocationNotFoundException e) {
                return new ResponseTo(false, e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseTo(false, "User not found", HttpStatus.NOT_FOUND);
    }

    private void editAnnouncement(AnnouncementPo announcementPo, AnnouncementTo announcementTo) throws
            InterruptedException, ParseException, IOException {
        JSONObject addressFromJson = reverseGeocodingService.getFullLocationData(announcementTo.getDestinationFrom());
        JSONObject addressToJson = reverseGeocodingService.getFullLocationData(announcementTo.getDestinationTo());

//        String fromAddress = addressFromJson.get("name") + ", " + addressFromJson.get("locality") + ", " + addressFromJson.get("country");
        String fromAddress = UnwrapResponseHelper.unwrapPositionStackSingleResponse(addressFromJson);
//        String toAddress = addressToJson.get("name") + ", " + addressToJson.get("locality") + ", " + addressToJson.get("country");
        String toAddress = UnwrapResponseHelper.unwrapPositionStackSingleResponse(addressToJson);
        announcementPo.getInitialLocationPo().setLatitude(announcementTo.getDestinationFrom().getLatitude());
        announcementPo.getInitialLocationPo().setLongitude(announcementTo.getDestinationFrom().getLongitude());
        announcementPo.getInitialLocationPo().setAddress(fromAddress);

        announcementPo.getFinalLocationPo().setLatitude(announcementTo.getDestinationTo().getLatitude());
        announcementPo.getFinalLocationPo().setLongitude(announcementTo.getDestinationTo().getLongitude());
        announcementPo.getFinalLocationPo().setAddress(toAddress);

        announcementPo.setRequireTransportWithClient(announcementTo.isRequireTransportWithClient());
        announcementPo.setAmount(new BigDecimal(announcementTo.getAmount()));
        announcementPo.setWeightUnit(announcementTo.getWeightUnit());
        Set<PackagePo> packagePos = new HashSet<>();
        announcementService.deleteAllByAnnouncement(announcementPo);
        announcementTo.getPackages().forEach(packageTo ->
                packagePos.add(new PackagePo(
                        new BigDecimal(packageTo.getPackageLength()),
                        packageTo.getLengthUnit(),
                        new BigDecimal(packageTo.getPackageWidth()),
                        packageTo.getWidthUnit(),
                        new BigDecimal(packageTo.getPackageHeight()),
                        packageTo.getHeightUnit(),
                        packageTo.getPackageWeight() != null ? new BigDecimal(packageTo.getPackageWeight()) : null,
                        announcementPo
                )));
        announcementService.saveAllPackages(packagePos);
    }

    private void addAnnouncement(UserPo author, AnnouncementTo announcementTo) throws InterruptedException, ParseException, IOException, LocationNotFoundException {
        JSONObject addressFromJson;
        JSONObject addressToJson;
        if (announcementTo.getDestinationFrom().getLongitude() == null && announcementTo.getDestinationFrom().getAddress() != null) {
            LocationTo locationTo = announcementTo.getDestinationFrom();
            addressFromJson = forwardGeocodingService.getFullLocationData(locationTo);
            if (addressFromJson == null) {
                throw new LocationNotFoundException("Address not found: " + locationTo.getAddress());
            }
        }
        else {
            addressFromJson = reverseGeocodingService.getFullLocationData(announcementTo.getDestinationFrom());
        }
        if (announcementTo.getDestinationTo().getLongitude() == null) {

            LocationTo locationTo = announcementTo.getDestinationTo();
            addressToJson = forwardGeocodingService.getFullLocationData(locationTo);
            if (addressToJson == null) {
                throw new LocationNotFoundException("Address not found: " + locationTo.getAddress());
            }
        }
        else {
            addressToJson = reverseGeocodingService.getFullLocationData(announcementTo.getDestinationTo());
        }

        LocationTo initialLocationTo;
        LocationTo finalLocationTo;
        if (Boolean.TRUE.equals(Boolean.valueOf(emergencyGeocodingMode))) {
            initialLocationTo = UnwrapResponseHelper.convertMapquestSingleResponse(addressFromJson);
            finalLocationTo = UnwrapResponseHelper.convertMapquestSingleResponse(addressToJson);
        } else {
            initialLocationTo = UnwrapResponseHelper.convertPositionStackSingleResponse(addressFromJson);
            finalLocationTo = UnwrapResponseHelper.convertPositionStackSingleResponse(addressToJson);
        }

        AnnouncementPo announcement = new AnnouncementPo(
                new LocationPo(initialLocationTo.getLatitude(), initialLocationTo.getLongitude(), initialLocationTo.getAddress()),
                new LocationPo(finalLocationTo.getLatitude(), finalLocationTo.getLongitude(), finalLocationTo.getAddress()),
                author,
                new BigDecimal(announcementTo.getAmount()),
                announcementTo.isRequireTransportWithClient(),
                announcementTo.getWeightUnit());

        if (announcementTo.getId() != null) {
            announcement.setId(announcementTo.getId());
        }

        announcementService.save(announcement);
        Set<PackagePo> packagePos = new HashSet<>();
        announcementTo.getPackages().forEach(packageTo ->
                packagePos.add(new PackagePo(
                        new BigDecimal(packageTo.getPackageLength()),
                        packageTo.getLengthUnit(),
                        new BigDecimal(packageTo.getPackageWidth()),
                        packageTo.getWidthUnit(),
                        new BigDecimal(packageTo.getPackageHeight()),
                        packageTo.getHeightUnit(),
                        packageTo.getPackageWeight() != null ? new BigDecimal(packageTo.getPackageWeight()) : null,
                        announcement
                )));
        announcementService.saveAllPackages(packagePos);
    }

    private static class LocationNotFoundException extends Exception {
        public LocationNotFoundException(String message) {
            super(message);
        }
    }
}
