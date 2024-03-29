package com.example.rentiaserver.delivery.repository;

import static com.example.rentiaserver.base.ApplicationConstants.Sql.ORDER_BY_CREATED_AT;

import com.example.rentiaserver.delivery.model.po.MessagePo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<MessagePo, Long> {

    @Query(value = "SELECT * FROM TB_MESSAGE WHERE SENDER_ID = ?1 AND MESSAGE_TYPE != 'INFO'" + ORDER_BY_CREATED_AT,
            nativeQuery = true)
    List<MessagePo> findAllBySender(Long senderId);

    @Query(value = "SELECT * FROM TB_MESSAGE WHERE RECEIVER_ID = ?1 " + ORDER_BY_CREATED_AT, nativeQuery = true)
    List<MessagePo> findAllByReceiver(Long receiverId);

}
