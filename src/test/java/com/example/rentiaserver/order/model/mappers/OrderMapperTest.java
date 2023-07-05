package com.example.rentiaserver.order.model.mappers;

import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.order.model.po.PackagePo;
import com.example.rentiaserver.order.model.to.OrderTo;
import com.example.rentiaserver.order.tool.PackagesHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderMapperTest {

    @Test
    public void shouldReturnNullWhenNullProvided() {
        // Given
        OrderPo order = null;

        // When
        OrderTo mappedOrder = OrderMapper.mapOrderPoToTo(order);

        // Then
        assertNull(mappedOrder);
    }

    @Test
    public void shouldReturnMappedOrderToWhenPoProvided() {
        // Given
        OrderPo order = new OrderPo();
        order.setId(1L);
        order.setCreatedAt(new Date());
        order.setRequireTransportWithClient(true);
        order.setWeightUnit("test_weight_unit");
        order.setSalary(new BigDecimal("1"));

        PackagePo packagePo = new PackagePo();
        packagePo.setWeight(new BigDecimal("10"));
        order.setPackagesPos(Collections.singleton(packagePo));

        // When
        OrderTo mappedOrder = OrderMapper.mapOrderPoToTo(order);

        // Then
        assertTrue(isCorrectlyMapped(mappedOrder, order));
    }

    private boolean isCorrectlyMapped(OrderTo mappedOrder, OrderPo order) {
        return mappedOrder.getId().equals(order.getId())
                && mappedOrder.getCreatedAt().equals(order.getCreatedAt())
                && mappedOrder.getRequireTransportWithClient().equals(order.isRequireTransportWithClient())
                && mappedOrder.getWeightUnit().equals(order.getWeightUnit())
                && mappedOrder.getWeight()
                        .equals(PackagesHelper.sumPackagesWeights(mappedOrder.getPackages()).toString())
                && mappedOrder.getSalary().equals(order.getSalary().toString());

    }
}