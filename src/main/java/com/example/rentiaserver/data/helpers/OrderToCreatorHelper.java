package com.example.rentiaserver.data.helpers;

import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.geolocation.po.LocationPo;
import com.example.rentiaserver.data.to.*;
import com.example.rentiaserver.geolocation.to.LocationTo;

import java.util.Set;
import java.util.stream.Collectors;

public final class OrderToCreatorHelper {
    public static OrderTo create(OrderPo orderPo) {
        LocationPo destinationFrom = orderPo.getInitialLocationPo();
        LocationPo destinationTo = orderPo.getFinalLocationPo();
        Set<PackageTo> packageTos =
                orderPo.getPackagesPos().stream().map(packagePo -> new PackageTo(
                        packagePo.getId(),
                        String.valueOf(packagePo.getCreatedAt()),
                        String.valueOf(packagePo.getPackageLength()),
                        packagePo.getLengthUnit(),
                        String.valueOf(packagePo.getPackageWidth()),
                        packagePo.getWidthUnit(),
                        String.valueOf(packagePo.getPackageHeight()),
                        packagePo.getHeightUnit(),
                        String.valueOf(packagePo.getWeight())
                )).collect(Collectors.toSet());
        return new OrderTo(
                orderPo.getId(),
                String.valueOf(orderPo.getCreatedAt()),
                new LocationTo(
                        destinationFrom.getId(),
                        String.valueOf(destinationFrom.getCreatedAt()),
                        destinationFrom.getLatitude(),
                        destinationFrom.getLongitude(),
                        destinationFrom.getAddress()
                ),
                new LocationTo(
                        destinationTo.getId(),
                        String.valueOf(destinationTo.getCreatedAt()),
                        destinationTo.getLatitude(),
                        destinationTo.getLongitude(),
                        destinationTo.getAddress()
                ),
                packageTos,
                orderPo.getAuthorPo().getId(),
                orderPo.getSalary() != null ? orderPo.getSalary().toString() : null,
                orderPo.isRequireTransportWithClient(),
                String.valueOf(PackagesWeightCounterHelper.sumPackagesWeights(packageTos)),
                orderPo.getWeightUnit()
        );
    }
}
