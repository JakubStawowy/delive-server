package com.example.rentiaserver.delivery.repository;

import static com.example.rentiaserver.base.ApplicationConstants.Sql.ORDER_BY_CREATED_AT;

import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.delivery.model.po.DeliveryPo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends CrudRepository<DeliveryPo, Long> {

    @Query(value = "SELECT * FROM TB_DELIVERY WHERE USER_ID = ?1 " + ORDER_BY_CREATED_AT, nativeQuery = true)
    List<DeliveryPo> findAllByDeliverer(Long delivererId);

    @Query(value = "SELECT D.ID, D.CREATED_AT, D.IS_ARCHIVED, D.FINISHED_AT, D.STARTED_AT, D.DELIVERY_STATE, D.ORDER_ID, D.USER_ID, D.VERSION " +
            "FROM TB_DELIVERY D JOIN TB_ORDER A on D.ORDER_ID = A.ID WHERE A.AUTHOR_ID = ?1 " + ORDER_BY_CREATED_AT, nativeQuery = true)
    List<DeliveryPo> findAllByPrincipal(Long principalId);

    Optional<DeliveryPo> findByOrderPoAndUserPo(OrderPo orderPo, UserPo userPo);
}

