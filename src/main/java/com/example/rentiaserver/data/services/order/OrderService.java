package com.example.rentiaserver.data.services.order;

import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.data.po.PackagePo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.data.to.OrderTo;
import com.example.rentiaserver.data.to.PackageTo;
import com.example.rentiaserver.data.util.EntityNotFoundException;
import com.example.rentiaserver.data.util.LocationNotFoundException;
import com.example.rentiaserver.data.util.UnsupportedArgumentException;
import com.example.rentiaserver.geolocation.api.GeocodingServiceResolver;
import com.example.rentiaserver.geolocation.api.LocationType;
import com.example.rentiaserver.geolocation.api.IGeocodingService;
import com.example.rentiaserver.geolocation.po.LocationPo;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
public final class OrderService {

    private final GeocodingServiceResolver geocodingServiceResolver;

    private final OrderRepository orderDao;

    private final OrderDao filteredOrdersDao;

    private final PackageDao packageDao;

    @Autowired
    public OrderService(
            GeocodingServiceResolver geocodingServiceResolver,
            OrderRepository orderDao,
            OrderDao filteredOrdersDao,
            PackageDao packageDao) {

        this.geocodingServiceResolver = geocodingServiceResolver;
        this.orderDao = orderDao;
        this.filteredOrdersDao = filteredOrdersDao;
        this.packageDao = packageDao;
    }


    public void save(OrderPo orderPo) {
        orderDao.save(orderPo);
    }

    public void archiveOrderById(Long id) throws EntityNotFoundException {
        OrderPo order = orderDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(OrderPo.class, id));

        order.setArchived(true);
        orderDao.save(order);
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

    public List<OrderPo> findOrdersByAddresses(String initialAddress,
                                               String finalAddress,
                                               String minimalSalary,
                                               Boolean requireTransportWithClient,
                                               Boolean sortBySalary) {

        return filteredOrdersDao.findOrdersByAddresses(
                initialAddress,
                finalAddress,
                minimalSalary,
                requireTransportWithClient,
                sortBySalary);
    }


    public void editOrder(OrderPo orderPo, OrderTo orderTo)
            throws LocationNotFoundException, ParseException,
            IOException, InterruptedException, UnsupportedArgumentException {

        Pair<LocationTo, LocationTo> locations = getLocationData(orderTo);

        LocationTo initialLocationTo = locations.getFirst();
        LocationTo finalLocationTo = locations.getSecond();

        LocationPo initialLocationPo = orderPo.getInitialLocationPo();

        initialLocationPo.setLatitude(initialLocationTo.getLatitude());
        initialLocationPo.setLongitude(initialLocationTo.getLongitude());
        initialLocationPo.setAddress(initialLocationTo.getAddress());

        LocationPo finalLocationPo = orderPo.getFinalLocationPo();

        finalLocationPo.setLatitude(finalLocationTo.getLatitude());
        finalLocationPo.setLongitude(finalLocationTo.getLongitude());
        finalLocationPo.setAddress(finalLocationTo.getAddress());

        orderPo.setRequireTransportWithClient(orderTo.isRequireTransportWithClient());
        orderPo.setSalary(new BigDecimal(orderTo.getSalary()));
        orderPo.setWeightUnit(orderTo.getWeightUnit());

        deleteAllPackagesByOrder(orderPo);

        savePackages(orderTo, orderPo);
    }

    public void addOrder(UserPo author, OrderTo orderTo)
            throws InterruptedException, ParseException, IOException,
            LocationNotFoundException, UnsupportedArgumentException {

        Pair<LocationTo, LocationTo> locations = getLocationData(orderTo);
        LocationTo initialLocationTo = locations.getFirst();
        LocationTo finalLocationTo = locations.getSecond();

        OrderPo orderPo = new OrderPo(
                new LocationPo(initialLocationTo.getLatitude(),
                        initialLocationTo.getLongitude(),
                        initialLocationTo.getAddress()),
                new LocationPo(finalLocationTo.getLatitude(),
                        finalLocationTo.getLongitude(),
                        finalLocationTo.getAddress()),
                author,
                new BigDecimal(orderTo.getSalary()),
                orderTo.isRequireTransportWithClient(),
                orderTo.getWeightUnit());

        Long orderId = orderTo.getId();
        if (orderId != null) {
            orderPo.setId(orderId);
        }

        save(orderPo);
        savePackages(orderTo, orderPo);
    }

    private Pair<LocationTo, LocationTo> getLocationData(OrderTo orderTo)
            throws LocationNotFoundException, InterruptedException, ParseException, IOException, UnsupportedArgumentException {

        LocationTo destinationFrom = orderTo.getDestinationFrom();
        LocationTo destinationTo = orderTo.getDestinationTo();

        LocationType locationType = extractCommonType(destinationFrom, destinationTo);

        IGeocodingService geocodingService = geocodingServiceResolver.resolveGeocodingService(locationType);

        LocationTo addressFrom = geocodingService.getSingleLocationData(destinationFrom)
                .orElseThrow(() -> new LocationNotFoundException(
                        "Address not found: " + destinationFrom.getAddress()));

        LocationTo addressTo = geocodingService.getSingleLocationData(destinationTo)
                .orElseThrow(() -> new LocationNotFoundException("Address not found: " + destinationTo.getAddress()));

        return Pair.of(addressFrom, addressTo);
    }

    private LocationType extractCommonType(LocationTo destinationFrom, LocationTo destinationTo) {
        LocationType locationType = destinationFrom.getLocationType();

        if (locationType == null) {
            throw new IllegalArgumentException("Location type cannot be null.");
        }

        if (!locationType.equals(destinationTo.getLocationType())) {
            throw new IllegalArgumentException("Location types of two locations are not equal.");
        }

        return locationType;
    }

    private void savePackages(OrderTo orderTo, OrderPo orderPo) {

        Set<PackagePo> packagePos = orderTo.getPackages()
                .stream()
                .map(packageTo -> mapPackageToToPo(packageTo, orderPo))
                .collect(Collectors.toSet());

        saveAllPackages(packagePos);
    }

    private PackagePo mapPackageToToPo(PackageTo packageTo, OrderPo orderPo) {
        return new PackagePo(
                new BigDecimal(packageTo.getPackageLength()),
                packageTo.getLengthUnit(),
                new BigDecimal(packageTo.getPackageWidth()),
                packageTo.getWidthUnit(),
                new BigDecimal(packageTo.getPackageHeight()),
                packageTo.getHeightUnit(),
                packageTo.getPackageWeight() != null ? new BigDecimal(packageTo.getPackageWeight()) : null,
                orderPo
        );
    }

}