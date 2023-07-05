package com.example.rentiaserver.order.model.mappers;

import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.order.model.po.PackagePo;
import com.example.rentiaserver.order.model.to.PackageTo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PackageMapperTest {

    @Test
    void shouldReturnNullWhenNullToProvided() {

        // Given
        PackageTo testPackage = null;
        OrderPo testOrder = new OrderPo();

        // When
        PackagePo mappedPackage = PackageMapper.mapPackageToToPo(testPackage, testOrder);

        // Then
        assertNull(mappedPackage);
    }

    @Test
    void shouldReturnNullWhenNullPoProvided() {

        // Given
        PackagePo testPackage = null;

        // When
        PackageTo mappedPackage = PackageMapper.mapPackagePoToTo(testPackage);

        // Then
        assertNull(mappedPackage);
    }

    @Test
    void shouldReturnMappedPackagePoWhenToProvided() {

        // Given
        PackageTo testPackage = PackageTo.builder()
                .weight("10")
                .length("20")
                .lengthUnit("cm")
                .width("30")
                .widthUnit("m")
                .height("40")
                .heightUnit("mm")
                .build();

        OrderPo testOrder = new OrderPo();

        // When
        PackagePo mappedPackage = PackageMapper.mapPackageToToPo(testPackage, testOrder);

        // Then
        assertTrue(isCorrectlyMapped(testPackage, mappedPackage));
    }

    @Test
    void shouldReturnMappedPackageToWhenPoProvided() {
        // Given
        PackagePo testPackage = new PackagePo();
        testPackage.setWeight(new BigDecimal("10"));
        testPackage.setLength(new BigDecimal("20"));
        testPackage.setLengthUnit("cm");
        testPackage.setWidth(new BigDecimal("30"));
        testPackage.setWidthUnit("m");
        testPackage.setHeight(new BigDecimal("40"));
        testPackage.setHeightUnit("mm");

        // When
        PackageTo mappedPackage = PackageMapper.mapPackagePoToTo(testPackage);

        // Then
        assertTrue(isCorrectlyMapped(mappedPackage, testPackage));
    }

    private boolean isCorrectlyMapped(PackageTo packageTo, PackagePo packagePo) {
        return packageTo.getWeight().equals(packagePo.getWeight().toString())
                && packageTo.getLength().equals(packagePo.getLength().toString())
                && packageTo.getLengthUnit().equals(packagePo.getLengthUnit())
                && packageTo.getHeight().equals(packagePo.getHeight().toString())
                && packageTo.getHeightUnit().equals(packagePo.getHeightUnit())
                && packageTo.getWidth().equals(packagePo.getWidth().toString())
                && packageTo.getWidthUnit().equals(packagePo.getWidthUnit());
    }
}