package com.example.rentiaserver.order.repository;

import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.order.model.po.PackagePo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends CrudRepository<PackagePo, Long> {

    void deleteAllByOrderPo(OrderPo orderPo);
}
