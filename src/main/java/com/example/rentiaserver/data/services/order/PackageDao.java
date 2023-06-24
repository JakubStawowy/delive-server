package com.example.rentiaserver.data.services.order;


import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.data.po.PackagePo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PackageDao extends CrudRepository<PackagePo, Long> {
    //    @Query(value = "UPDATE TB_PACKAGE SET IS_ARCHIVED=TRUE WHERE ORDER_ID=", nativeQuery = true)
    void deleteAllByOrderPo(OrderPo orderPo);
}
