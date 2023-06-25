package com.example.rentiaserver.order.service;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.base.exception.LocationNotFoundException;
import com.example.rentiaserver.base.exception.UnsupportedArgumentException;
import com.example.rentiaserver.geolocation.api.GeocodingServiceResolver;
import com.example.rentiaserver.geolocation.api.IGeocodingService;
import com.example.rentiaserver.geolocation.api.LocationType;
import com.example.rentiaserver.geolocation.model.po.LocationPo;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import com.example.rentiaserver.order.model.mappers.OrderMapper;
import com.example.rentiaserver.order.model.mappers.PackageMapper;
import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.order.model.po.PackagePo;
import com.example.rentiaserver.order.model.to.OrderTo;
import com.example.rentiaserver.order.repository.OrderDao;
import com.example.rentiaserver.order.repository.OrderRepository;
import com.example.rentiaserver.order.repository.PackageRepository;
import com.example.rentiaserver.order.tool.PackagesWeightCounterHelper;
import com.example.rentiaserver.user.model.po.UserPo;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public final class OrderService {

    private final GeocodingServiceResolver geocodingServiceResolver;

    private final OrderRepository orderDao;

    private final OrderDao filteredOrdersDao;

    private final PackageRepository packageDao;

    @Autowired
    public OrderService(
            GeocodingServiceResolver geocodingServiceResolver,
            OrderRepository orderDao,
            OrderDao filteredOrdersDao,
            PackageRepository packageDao) {

        this.geocodingServiceResolver = geocodingServiceResolver;
        this.orderDao = orderDao;
        this.filteredOrdersDao = filteredOrdersDao;
        this.packageDao = packageDao;
    }


    public void save(OrderPo orderPo) {
        orderDao.save(orderPo);
    }

    public void archiveById(Long id) throws EntityNotFoundException {
        OrderPo order = orderDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(OrderPo.class, id));
        archive(order);
    }

    public void archive(OrderPo order) {
        order.setArchived(true);
        save(order);
    }

    public OrderTo getOrderToById(Long orderId) throws EntityNotFoundException {
        return OrderMapper.mapOrderPoToTo(getOrderById(orderId));
    }

    public OrderPo getOrderById(Long orderId) throws EntityNotFoundException {
        return orderDao.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(OrderPo.class, orderId));
    }

    public List<OrderTo> getAllOrderTos() {
        return orderDao.findAll()
                .stream()
                .map(OrderMapper::mapOrderPoToTo)
                .collect(Collectors.toList());
    }

    public void saveAllPackages(Set<PackagePo> packagePos) {
        packageDao.saveAll(packagePos);
    }

    public void deleteAllPackagesByOrder(OrderPo orderPo) {
        packageDao.deleteAllByOrderPo(orderPo);
    }

    public List<OrderTo> queryOrderTos(String initialAddress,
                                       String finalAddress,
                                       String minimalSalary,
                                       String maxWeight,
                                       Boolean requireTransportWithClient,
                                       Boolean sortBySalary,
                                       Boolean sortByWeight) {


        List<OrderPo> orders = filteredOrdersDao.queryOrderTos(
                initialAddress,
                finalAddress,
                minimalSalary,
                requireTransportWithClient,
                sortBySalary);

        return orders.stream()
                .map(OrderMapper::mapOrderPoToTo)
                .filter(order -> isBelowMaxWeight(order, maxWeight))
                // TODO put sorting by summed weight in Criteria query
                .sorted(evaluateComparator(sortByWeight))
                .collect(Collectors.toList());

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

        orderPo.setRequireTransportWithClient(orderTo.getRequireTransportWithClient());
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
                orderTo.getRequireTransportWithClient(),
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

        LocationType locationType = extractCommonLocationType(destinationFrom, destinationTo);

        IGeocodingService geocodingService = geocodingServiceResolver.resolveGeocodingService(locationType);

        LocationTo addressFrom = geocodingService.getSingleLocationData(destinationFrom)
                .orElseThrow(() -> new LocationNotFoundException(
                        "Address not found: " + destinationFrom.getAddress()));

        LocationTo addressTo = geocodingService.getSingleLocationData(destinationTo)
                .orElseThrow(() -> new LocationNotFoundException("Address not found: " + destinationTo.getAddress()));

        return Pair.of(addressFrom, addressTo);
    }

    private LocationType extractCommonLocationType(LocationTo destinationFrom, LocationTo destinationTo) {
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
                .map(packageTo -> PackageMapper.mapPackageToToPo(packageTo, orderPo))
                .collect(Collectors.toSet());

        saveAllPackages(packagePos);
    }

    private Comparator<OrderTo> evaluateComparator(Boolean sortByWeight) {
        return Boolean.TRUE.equals(sortByWeight)
                ? Comparator.comparing(orderTo -> new BigDecimal(orderTo.getWeight()))
                : Comparator.comparing(orderTo -> 0);
    }


    private boolean isBelowMaxWeight(OrderTo order, String maxWeight) {
        return !(maxWeight != null && !maxWeight.isEmpty())
                || PackagesWeightCounterHelper.sumPackagesWeights(order.getPackages())
                .compareTo(new BigDecimal(maxWeight)) < 0;
    }
}