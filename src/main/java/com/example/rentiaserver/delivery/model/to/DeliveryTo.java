package com.example.rentiaserver.delivery.model.to;

import com.example.rentiaserver.base.model.to.BaseEntityTo;
import com.example.rentiaserver.delivery.api.DeliveryState;
import com.example.rentiaserver.order.model.to.OrderTo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTo extends BaseEntityTo {
    private Long delivererId;
    private OrderTo order;
    private DeliveryState deliveryState;
}
