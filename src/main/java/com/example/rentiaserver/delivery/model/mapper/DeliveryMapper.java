package com.example.rentiaserver.delivery.model.mapper;

import com.example.rentiaserver.delivery.model.po.DeliveryPo;
import com.example.rentiaserver.delivery.model.to.DeliveryTo;
import com.example.rentiaserver.order.model.mappers.OrderMapper;

public class DeliveryMapper {

    public static DeliveryTo mapDeliveryPoToTo(DeliveryPo deliveryPo) {
        return DeliveryTo.builder()
                .id(deliveryPo.getId())
                .createdAt(deliveryPo.getCreatedAt())
                .delivererId(deliveryPo.getUserPo().getId())
                .deliveryState(deliveryPo.getDeliveryState())
                .order(OrderMapper.mapOrderPoToTo(deliveryPo.getOrderPo()))
                .build();
    }
}
