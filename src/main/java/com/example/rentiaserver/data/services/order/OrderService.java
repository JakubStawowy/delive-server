package com.example.rentiaserver.data.services.order;

import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.data.po.PackagePo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.data.to.OrderTo;
import com.example.rentiaserver.geolocation.converter.*;
import com.example.rentiaserver.geolocation.helpers.UnwrapResponseHelper;
import com.example.rentiaserver.geolocation.po.LocationPo;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@PropertySource("classpath:application.properties")
public final class OrderService {

    private final IGeocodingService forwardGeocodingService;
    private final IGeocodingService reverseGeocodingService;
    private final String emergencyGeocodingMode;
    private final OrderDao orderDao;
    private final FilteredOrderService filteredOrdersDao;
    private final PackageDao packageDao;

    @Autowired
    public OrderService(OrderDao orderDao, FilteredOrderService filteredOrdersDao, PackageDao packageRepository,
                        ForwardGeocodingService forwardGeocodingService, ReverseGeocodingService reverseGeocodingService,
                               ForwardEmergencyGeocodingService forwardEmergencyGeocodingService,
                               ReverseEmergencyGeocodingService reverseEmergencyGeocodingService,
                               @Value("${geocoding.mode.emergency}") String emergencyGeocodingMode) {
        this.orderDao = orderDao;
        this.filteredOrdersDao = filteredOrdersDao;
        this.packageDao = packageRepository;
        this.emergencyGeocodingMode = emergencyGeocodingMode;
        if (Boolean.TRUE.equals(Boolean.valueOf(emergencyGeocodingMode))) {
            this.forwardGeocodingService = forwardEmergencyGeocodingService;
            this.reverseGeocodingService = reverseEmergencyGeocodingService;
        } else {
            this.forwardGeocodingService = forwardGeocodingService;
            this.reverseGeocodingService = reverseGeocodingService;
        }
    }

    public void save(OrderPo orderPo) {
        orderDao.save(orderPo);
    }

    public void archiveOrderById(Long id) {
        Optional<OrderPo> optionalOrderPo = orderDao.findById(id);
        optionalOrderPo.ifPresent(orderPo -> {
            orderPo.setArchived(true);
            orderDao.save(orderPo);
        });
    }

    public Optional<OrderPo> getOrderById(Long id) {
        return orderDao.findById(id);
    }

    public List<OrderPo> getAllOrders() {
        return orderDao.findAll();
    }

    public void saveAllPackages(Set<PackagePo> packagePos) {
        packageDao.saveAll(packagePos);
    }

    public void deleteAllPackagesByOrder(OrderPo orderPo) {
        packageDao.deleteAllByOrderPo(orderPo);
    }

    public List<OrderPo> findOrdersByAddresses(String initialAddress, String finalAddress, String minimalSalary,
                                               String requireTransportWithClient, boolean sortBySalary) {
        return filteredOrdersDao.findOrdersByAddresses(initialAddress, finalAddress, minimalSalary, requireTransportWithClient, sortBySalary);
    }


    public void editOrder(OrderPo orderPo, OrderTo orderTo) throws
            InterruptedException, ParseException, IOException, LocationNotFoundException {
        Pair<LocationTo, LocationTo> locations = getLocationData(orderTo);
        LocationTo initialLocationTo = locations.getFirst();
        LocationTo finalLocationTo = locations.getSecond();

        orderPo.getInitialLocationPo().setLatitude(initialLocationTo.getLatitude());
        orderPo.getInitialLocationPo().setLongitude(initialLocationTo.getLongitude());
        orderPo.getInitialLocationPo().setAddress(initialLocationTo.getAddress());

        orderPo.getFinalLocationPo().setLatitude(finalLocationTo.getLatitude());
        orderPo.getFinalLocationPo().setLongitude(finalLocationTo.getLongitude());
        orderPo.getFinalLocationPo().setAddress(finalLocationTo.getAddress());

        orderPo.setRequireTransportWithClient(orderTo.isRequireTransportWithClient());
        orderPo.setSalary(new BigDecimal(orderTo.getSalary()));
        orderPo.setWeightUnit(orderTo.getWeightUnit());
        deleteAllPackagesByOrder(orderPo);
        savePackages(orderTo, orderPo);
    }

    public void addOrder(UserPo author, OrderTo orderTo) throws InterruptedException, ParseException, IOException, LocationNotFoundException {
        Pair<LocationTo, LocationTo> locations = getLocationData(orderTo);
        LocationTo initialLocationTo = locations.getFirst();
        LocationTo finalLocationTo = locations.getSecond();

        OrderPo orderPo = new OrderPo(
                new LocationPo(initialLocationTo.getLatitude(), initialLocationTo.getLongitude(), initialLocationTo.getAddress()),
                new LocationPo(finalLocationTo.getLatitude(), finalLocationTo.getLongitude(), finalLocationTo.getAddress()),
                author,
                new BigDecimal(orderTo.getSalary()),
                orderTo.isRequireTransportWithClient(),
                orderTo.getWeightUnit());

        if (orderTo.getId() != null) {
            orderPo.setId(orderTo.getId());
        }

        save(orderPo);
        savePackages(orderTo, orderPo);
    }

    private Pair<LocationTo, LocationTo> getLocationData(OrderTo orderTo) throws LocationNotFoundException, InterruptedException, ParseException, IOException {
        JSONObject addressFromJson;
        JSONObject addressToJson;
        if (orderTo.getDestinationFrom().getLongitude() == null && orderTo.getDestinationFrom().getAddress() != null) {
            LocationTo locationTo = orderTo.getDestinationFrom();
            addressFromJson = forwardGeocodingService.getFullLocationData(locationTo);
            if (addressFromJson == null) {
                throw new LocationNotFoundException("Address not found: " + locationTo.getAddress());
            }
        }
        else {
            addressFromJson = reverseGeocodingService.getFullLocationData(orderTo.getDestinationFrom());
        }
        if (orderTo.getDestinationTo().getLongitude() == null) {

            LocationTo locationTo = orderTo.getDestinationTo();
            addressToJson = forwardGeocodingService.getFullLocationData(locationTo);
            if (addressToJson == null) {
                throw new LocationNotFoundException("Address not found: " + locationTo.getAddress());
            }
        }
        else {
            addressToJson = reverseGeocodingService.getFullLocationData(orderTo.getDestinationTo());
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
        return Pair.of(initialLocationTo, finalLocationTo);
    }

    private void savePackages(OrderTo orderTo, OrderPo orderPo) {

        Set<PackagePo> packagePos = new HashSet<>();
        orderTo.getPackages().forEach(packageTo ->
                packagePos.add(new PackagePo(
                        new BigDecimal(packageTo.getPackageLength()),
                        packageTo.getLengthUnit(),
                        new BigDecimal(packageTo.getPackageWidth()),
                        packageTo.getWidthUnit(),
                        new BigDecimal(packageTo.getPackageHeight()),
                        packageTo.getHeightUnit(),
                        packageTo.getPackageWeight() != null ? new BigDecimal(packageTo.getPackageWeight()) : null,
                        orderPo
                )));
        saveAllPackages(packagePos);
    }

    public static class LocationNotFoundException extends Exception {
        public LocationNotFoundException(String message) {
            super(message);
        }
    }
}