package com.example.rentiaserver.order.tool;

import com.example.rentiaserver.order.model.to.PackageTo;

import java.math.BigDecimal;
import java.util.Set;

public class PackagesWeightCounterHelper {

    public static BigDecimal sumPackagesWeights(Set<PackageTo> packageTos) {
        return packageTos.stream().map(packageTo -> new BigDecimal(packageTo.getWeight()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
