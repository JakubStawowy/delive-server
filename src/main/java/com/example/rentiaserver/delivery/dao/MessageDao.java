package com.example.rentiaserver.delivery.dao;

import com.example.rentiaserver.delivery.po.MessagePo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao extends CrudRepository<MessagePo, Long> {

    @Query(value = "SELECT * FROM MESSAGES WHERE SENDER_ID = ?1 ORDER BY CREATED_AT DESC", nativeQuery = true)
    List<MessagePo> findAllBySender(Long senderId);

    @Query(value = "SELECT * FROM MESSAGES WHERE RECEIVER_ID = ?1 ORDER BY CREATED_AT DESC", nativeQuery = true)
    List<MessagePo> findAllByReceiver(Long receiverId);

}
