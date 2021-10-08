package com.example.rentiaserver.data.dao;

import static com.example.rentiaserver.constants.ApplicationConstants.Sql.ORDER_BY_CREATED_AT_PREFIX;

import com.example.rentiaserver.data.po.FeedbackPo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends CrudRepository<FeedbackPo, Long> {
    @Query(value = "SELECT * FROM FEEDBACK WHERE USER_ID = ?1 " + ORDER_BY_CREATED_AT_PREFIX
            , nativeQuery = true)
    List<FeedbackPo> getFeedbackPosByUserPoId(Long id);
}
