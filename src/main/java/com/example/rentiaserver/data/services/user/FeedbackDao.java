package com.example.rentiaserver.data.services.user;

import com.example.rentiaserver.data.po.FeedbackPo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.rentiaserver.ApplicationConstants.Sql.ORDER_BY_CREATED_AT;

@Repository
interface FeedbackDao extends CrudRepository<FeedbackPo, Long> {
    @Query(value = "SELECT * FROM TB_FEEDBACK WHERE USER_ID = ?1 " + ORDER_BY_CREATED_AT
            , nativeQuery = true)
    List<FeedbackPo> getFeedbackPosByUserPoId(Long id);
}
