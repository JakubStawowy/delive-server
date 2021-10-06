package com.example.rentiaserver.delivery.dao;

import com.example.rentiaserver.delivery.po.DeliveryPo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDao extends CrudRepository<DeliveryPo, Long> {

    @Query(value = "SELECT * FROM DELIVERY WHERE USER_ID = ?1 ORDER BY CREATED_AT DESC", nativeQuery = true)
    List<DeliveryPo> findAllByDeliverer(Long delivererId);

    @Query(value = "SELECT D.ID, D.CREATED_AT, D.FINISHED_AT, D.DELIVERY_STATE, D.ANNOUNCEMENT_ID, D.USER_ID, D.IS_EDITABLE, D.VERSION " +
            "FROM DELIVERY D JOIN ANNOUNCEMENTS A on D.ANNOUNCEMENT_ID = A.ID LEFT OUTER JOIN USERS_ANNOUNCEMENTS UA on A.ID = UA.ANNOUNCEMENT_ID " +
//            "WHERE A.AUTHOR_ID = ?1 AND D.USER_ID != ?1 OR UA.USER_ID = ?1 ORDER BY CREATED_AT DESC", nativeQuery = true)
            "WHERE A.AUTHOR_ID = ?1 AND D.USER_ID != ?1 OR UA.USER_ID = ?1 ORDER BY CREATED_AT DESC", nativeQuery = true)
    List<DeliveryPo> findAllByPrincipal(Long principalId);
}

