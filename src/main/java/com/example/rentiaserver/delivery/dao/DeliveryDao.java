package com.example.rentiaserver.delivery.dao;

import static com.example.rentiaserver.ApplicationConstants.Sql.ORDER_BY_CREATED_AT;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryDao extends CrudRepository<DeliveryPo, Long> {

    @Query(value = "SELECT * FROM DELIVERIES WHERE USER_ID = ?1 " + ORDER_BY_CREATED_AT, nativeQuery = true)
    List<DeliveryPo> findAllByDeliverer(Long delivererId);

    @Query(value = "SELECT D.ID, D.CREATED_AT, D.IS_ARCHIVED, D.FINISHED_AT, D.DELIVERY_STATE, D.ANNOUNCEMENT_ID, D.USER_ID, D.VERSION " +
            "FROM DELIVERIES D JOIN ANNOUNCEMENTS A on D.ANNOUNCEMENT_ID = A.ID WHERE A.AUTHOR_ID = ?1 " + ORDER_BY_CREATED_AT, nativeQuery = true)
    List<DeliveryPo> findAllByPrincipal(Long principalId);

    Optional<DeliveryPo> findByAnnouncementPoAndUserPo(AnnouncementPo announcementPo, UserPo userPo);
}

