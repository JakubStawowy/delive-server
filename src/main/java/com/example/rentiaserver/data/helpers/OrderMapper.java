package com.example.rentiaserver.data.helpers;

import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.data.to.OrderTo;
import com.example.rentiaserver.data.to.PackageTo;
import com.example.rentiaserver.geolocation.po.LocationPo;
import com.example.rentiaserver.geolocation.to.LocationTo;

import java.util.Set;
import java.util.stream.Collectors;

public final class OrderMapper {

    public static OrderTo mapPersistentObjectToTransfer(OrderPo orderPo) {
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
                LocationTo.getBuilder()
                        .setAddress(destinationFrom.getAddress())
                        .setLatitude(destinationFrom.getLatitude())
                        .setLongitude(destinationFrom.getLongitude())
                        .setId(destinationFrom.getId())
                        .setCreatedAt(String.valueOf(destinationFrom.getCreatedAt()))
                        .build(),
                LocationTo.getBuilder()
                        .setAddress(destinationTo.getAddress())
                        .setLatitude(destinationTo.getLatitude())
                        .setLongitude(destinationTo.getLongitude())
                        .setId(destinationTo.getId())
                        .setCreatedAt(String.valueOf(destinationTo.getCreatedAt()))
                        .build(),
                packageTos,
                orderPo.getAuthorPo().getId(),
                orderPo.getSalary() != null ? orderPo.getSalary().toString() : null,
                orderPo.isRequireTransportWithClient(),
                String.valueOf(PackagesWeightCounterHelper.sumPackagesWeights(packageTos)),
                orderPo.getWeightUnit()
        );
    }
}
