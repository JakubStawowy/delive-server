package com.example.rentiaserver.delivery.services;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.delivery.dao.DeliveryDao;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.finance.dao.TransferDao;
import com.example.rentiaserver.finance.po.TransferPo;
import com.example.rentiaserver.maps.services.DistanceService;
import com.example.rentiaserver.maps.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryDao deliveryDao;
    private final UserService userService;
    private final DistanceService distanceService;
    private final TransferDao transferDao;

    @Autowired
    public DeliveryService(DeliveryDao deliveryDao, UserService userService, DistanceService distanceService, TransferDao transferDao) {
        this.deliveryDao = deliveryDao;
        this.userService = userService;
        this.distanceService = distanceService;
        this.transferDao = transferDao;
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

    public void save(TransferPo transferPo) {
        transferDao.save(transferPo);
    }

    public double getDistance(LocationTo initialLocation, LocationTo finalLocation) {
        return distanceService.getDistance(initialLocation, finalLocation);
    }
}
