package com.example.rentiaserver.order.tool;

import com.example.rentiaserver.order.model.to.PackageTo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PackagesHelperTest {

    @Test
    void shouldReturnZeroWhenNullProvided() {

        // Given
        Set<PackageTo> packageTos = null;

        // When
        BigDecimal sumWeights = PackagesHelper.sumPackagesWeights(packageTos);

        // Then
        assertEquals(BigDecimal.ZERO, sumWeights);
    }

    @Test
    void shouldReturnZeroWhenEmptyPackagesProvided() {

        // Given
        Set<PackageTo> packageTos = new HashSet<>();

        // When
        BigDecimal sumWeights = PackagesHelper.sumPackagesWeights(packageTos);

        // Then
        assertEquals(BigDecimal.ZERO, sumWeights);
    }

    @Test
    void shouldSumPackageWeightsCorrectlyWhenPackagesProvided() {

        // Given
        Set<PackageTo> packageTos = new HashSet<>();
        packageTos.add(createPackageTo("10.5"));
        packageTos.add(createPackageTo("5.2"));
        packageTos.add(createPackageTo("3.8"));

        // When
        BigDecimal sumWeights = PackagesHelper.sumPackagesWeights(packageTos);

        // Then
        assertEquals(new BigDecimal("19.5"), sumWeights);
    }

    private PackageTo createPackageTo(String weight) {
        return PackageTo.builder()
                .weight(weight)
                // Set other properties of PackageTo as needed for the test
                .build();
    }
}