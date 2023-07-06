package com.example.rentiaserver.delivery.model.mapper;

import com.example.rentiaserver.delivery.api.MessageType;
import com.example.rentiaserver.delivery.model.po.MessagePo;
import com.example.rentiaserver.delivery.model.to.OutgoingMessageTo;
import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.user.model.po.UserPo;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

class MessageMapperTest {

    @Test
    void shouldReturnNullWhenNullProvided() {

        // Given
        MessagePo messagePo = null;

        // When
        OutgoingMessageTo mappedMessage = MessageMapper.mapMessagePoToOutgoingMessageTo(messagePo);

        // Then
        assertNull(mappedMessage);
    }

    @Test
    void shouldReturnMappedMessageToWhenPoProvided() {

        // Given
        MessagePo messagePo = new MessagePo();
        messagePo.setId(1L);
        messagePo.setCreatedAt(new Date());
        messagePo.setMessage("test_message");
        messagePo.setVehicleRegistrationNumber("test_registration_number");
        messagePo.setPhoneNumber("test_phone_number");
        messagePo.setMessageType(MessageType.INFO);
        messagePo.setReplied(true);

        OrderPo order = new OrderPo();
        order.setId(1L);

        messagePo.setOrderPo(order);

        UserPo sender = new UserPo();
        sender.setId(1L);

        messagePo.setSenderPo(sender);

        UserPo receiver = new UserPo();
        receiver.setId(2L);

        messagePo.setReceiverPo(receiver);

        // When
        OutgoingMessageTo mappedMessage = MessageMapper.mapMessagePoToOutgoingMessageTo(messagePo);

        // Then
        assertTrue(isCorrectlyMapped(mappedMessage, messagePo));
    }

    private boolean isCorrectlyMapped(OutgoingMessageTo mappedMessage, MessagePo message) {
        return mappedMessage.getMessage().equals(message.getMessage())
                && mappedMessage.getMessageType().equals(message.getMessageType())
                && mappedMessage.getId().equals(message.getId())
                && mappedMessage.getCreatedAt().equals(message.getCreatedAt())
                && mappedMessage.getVehicleRegistrationNumber().equals(message.getVehicleRegistrationNumber())
                && mappedMessage.getPhoneNumber().equals(message.getPhoneNumber())
                && mappedMessage.isReplied() == message.isReplied()
                && mappedMessage.getOrderId().equals(message.getOrderPo().getId());
    }
}