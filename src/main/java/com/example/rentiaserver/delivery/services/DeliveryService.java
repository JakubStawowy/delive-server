package com.example.rentiaserver.delivery.services;

import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.dao.DeliveryDao;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryDao deliveryDao;
    private final UserRepository userRepository;

    @Autowired
    public DeliveryService(DeliveryDao deliveryDao, UserRepository userRepository) {
        this.deliveryDao = deliveryDao;
        this.userRepository = userRepository;
    }

    public Optional<UserPo> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<DeliveryPo> findDeliveryById(Long id) {
        return deliveryDao.findById(id);
    }

    public void save(UserPo deliverer, UserPo principal) {
        userRepository.saveAll(Arrays.asList(deliverer, principal));
    }

    public void save(DeliveryPo deliveryPo) {
        deliveryDao.save(deliveryPo);
    }
}
