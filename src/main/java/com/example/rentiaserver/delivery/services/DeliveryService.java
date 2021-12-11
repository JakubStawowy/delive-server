package com.example.rentiaserver.delivery.services;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.delivery.dao.DeliveryDao;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.geolocation.distance.IDistanceCalculator;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryDao deliveryDao;
    private final UserService userService;
    private final IDistanceCalculator distanceService;

    @Autowired
    public DeliveryService(DeliveryDao deliveryDao, UserService userService, IDistanceCalculator distanceService) {
        this.deliveryDao = deliveryDao;
        this.userService = userService;
        this.distanceService = distanceService;
    }

    public Optional<UserPo> findUserById(Long id) {
        return userService.findUserById(id);
    }

    public Optional<DeliveryPo> findDeliveryById(Long id) {
        return deliveryDao.findById(id);
    }

    public Optional<DeliveryPo> findDeliveryByAnnouncementPoAndUserPo(AnnouncementPo announcementPo, UserPo userPo) {
        return deliveryDao.findByAnnouncementPoAndUserPo(announcementPo, userPo);
    }

    public void save(UserPo userPo) {
        userService.saveUser(userPo);
    }

    public void save(DeliveryPo deliveryPo) {
        deliveryDao.save(deliveryPo);
    }

    public double getDistance(LocationTo initialLocation, LocationTo finalLocation) {
        return distanceService.getDistance(initialLocation, finalLocation);
    }
}
