package com.example.rentiaserver.delivery.dao;

import com.example.rentiaserver.delivery.po.DeliveryPo;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryRepository extends CrudRepository<DeliveryPo, Long> {
}
