package com.example.rentiaserver.delivery.dao;

import static com.example.rentiaserver.constants.ApplicationConstants.Sql.ORDER_BY_CREATED_AT_PREFIX;

import com.example.rentiaserver.delivery.po.MessagePo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao extends CrudRepository<MessagePo, Long> {

    @Query(value = "SELECT * FROM MESSAGES WHERE SENDER_ID = ?1 " + ORDER_BY_CREATED_AT_PREFIX, nativeQuery = true)
    List<MessagePo> findAllBySender(Long senderId);

    @Query(value = "SELECT * FROM MESSAGES WHERE RECEIVER_ID = ?1 " + ORDER_BY_CREATED_AT_PREFIX, nativeQuery = true)
    List<MessagePo> findAllByReceiver(Long receiverId);

}
