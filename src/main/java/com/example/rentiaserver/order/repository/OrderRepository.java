package com.example.rentiaserver.order.repository;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.order.model.po.OrderPo;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderPo, Long> {
    @Query(value = "SELECT * FROM TB_ORDER WHERE " + ApplicationConstants.Sql.NOT_ARCHIVED + " " + ApplicationConstants.Sql.ORDER_BY_CREATED_AT, nativeQuery = true)
    @NotNull
    List<OrderPo> findAll();
}