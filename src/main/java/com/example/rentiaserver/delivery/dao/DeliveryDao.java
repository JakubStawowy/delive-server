package com.example.rentiaserver.delivery.dao;

import static com.example.rentiaserver.constants.ApplicationConstants.Sql.ORDER_BY_CREATED_AT_PREFIX;

import com.example.rentiaserver.delivery.po.DeliveryPo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDao extends CrudRepository<DeliveryPo, Long> {

    @Query(value = "SELECT * FROM DELIVERIES WHERE USER_ID = ?1 " + ORDER_BY_CREATED_AT_PREFIX, nativeQuery = true)
    List<DeliveryPo> findAllByDeliverer(Long delivererId);

    @Query(value = "SELECT D.ID, D.CREATED_AT, D.FINISHED_AT, D.DELIVERY_STATE, D.ANNOUNCEMENT_ID, D.USER_ID, D.IS_EDITABLE, D.VERSION " +
            "FROM DELIVERIES D JOIN ANNOUNCEMENTS A on D.ANNOUNCEMENT_ID = A.ID WHERE A.AUTHOR_ID = ?1 " + ORDER_BY_CREATED_AT_PREFIX, nativeQuery = true)
    List<DeliveryPo> findAllByPrincipal(Long principalId);
}

