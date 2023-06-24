package com.example.rentiaserver.data.helpers;

import com.example.rentiaserver.data.to.PackageTo;

import java.math.BigDecimal;
import java.util.Set;

public class PackagesWeightCounterHelper {

    public static BigDecimal sumPackagesWeights(Set<PackageTo> packageTos) {
        return packageTos.stream().map(packageTo -> new BigDecimal(packageTo.getPackageWeight()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
