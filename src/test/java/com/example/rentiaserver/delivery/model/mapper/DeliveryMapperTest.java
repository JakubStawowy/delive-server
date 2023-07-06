package com.example.rentiaserver.delivery.model.mapper;

import com.example.rentiaserver.delivery.api.DeliveryState;
import com.example.rentiaserver.delivery.model.po.DeliveryPo;
import com.example.rentiaserver.delivery.model.to.DeliveryTo;
import com.example.rentiaserver.user.model.po.UserPo;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryMapperTest {

    @Test
    void shouldReturnNullWhenNullProvided() {

        // Given
        DeliveryPo user = null;

        // When
        DeliveryTo mappedDelivery = DeliveryMapper.mapDeliveryPoToTo(user);

        // Then
        assertNull(mappedDelivery);
    }

    @Test
    void shouldReturnMappedDeliveryToWhenPoProvided() {

        // Given
        UserPo user = new UserPo();
        user.setId(1L);

        DeliveryPo deliveryPo = new DeliveryPo();
        deliveryPo.setDeliveryState(DeliveryState.STARTED);
        deliveryPo.setCreatedAt(new Date());
        deliveryPo.setUserPo(user);

        // When
        DeliveryTo mappedDelivery = DeliveryMapper.mapDeliveryPoToTo(deliveryPo);

        // Then
        assertTrue(isCorrectlyMapped(mappedDelivery, deliveryPo));
    }

    private boolean isCorrectlyMapped(DeliveryTo mappedDelivery, DeliveryPo deliveryPo) {
        return mappedDelivery.getDeliveryState().equals(deliveryPo.getDeliveryState())
                && mappedDelivery.getCreatedAt().equals(deliveryPo.getCreatedAt())
                && mappedDelivery.getDelivererId().equals(deliveryPo.getUserPo().getId());
    }
}