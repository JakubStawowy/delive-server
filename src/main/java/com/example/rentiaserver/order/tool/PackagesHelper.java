package com.example.rentiaserver.order.tool;

import com.example.rentiaserver.order.model.to.PackageTo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class PackagesHelper {

    public static BigDecimal sumPackagesWeights(Set<PackageTo> packageTos) {

        return Optional.ofNullable(packageTos)
                .stream()
                .flatMap(Collection::stream)
                .map(packageTo -> new BigDecimal(packageTo.getWeight()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
