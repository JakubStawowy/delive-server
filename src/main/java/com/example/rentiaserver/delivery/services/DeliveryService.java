package com.example.rentiaserver.delivery.services;

import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.delivery.dao.DeliveryDao;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.geolocation.to.LocationTo;
import com.example.rentiaserver.geolocation.tool.HaversineDistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryDao deliveryDao;

    private final UserService userService;


    @Autowired
    public DeliveryService(DeliveryDao deliveryDao, UserService userService) {
        this.deliveryDao = deliveryDao;
        this.userService = userService;
    }

    public Optional<UserPo> findUserById(Long id) {
        return userService.findUserById(id);
    }

    public Optional<DeliveryPo> findDeliveryById(Long id) {
        return deliveryDao.findById(id);
    }

    public Optional<DeliveryPo> findDeliveryByOrderPoAndUserPo(OrderPo orderPo, UserPo userPo) {
        return deliveryDao.findByOrderPoAndUserPo(orderPo, userPo);
    }

    public void save(UserPo userPo) {
        userService.saveUser(userPo);
    }

    public void save(DeliveryPo deliveryPo) {
        deliveryDao.save(deliveryPo);
    }

    public double getDistance(LocationTo initialLocation, LocationTo finalLocation) {
        return HaversineDistanceCalculator.getDistance(initialLocation, finalLocation);
    }
}
