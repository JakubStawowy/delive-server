package com.example.rentiaserver.order.model.mappers;

import com.example.rentiaserver.geolocation.model.mapper.LocationMapper;
import com.example.rentiaserver.geolocation.model.po.LocationPo;
import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.order.model.to.OrderTo;
import com.example.rentiaserver.order.model.to.PackageTo;
import com.example.rentiaserver.order.tool.PackagesWeightCounterHelper;

import java.util.Set;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderTo mapOrderPoToTo(OrderPo orderPo) {
        LocationPo destinationFrom = orderPo.getInitialLocationPo();
        LocationPo destinationTo = orderPo.getFinalLocationPo();

        Set<PackageTo> packageTos = orderPo.getPackagesPos().stream()
                .map(PackageMapper::mapPackagePoToTo)
                .collect(Collectors.toSet());

        return OrderTo.builder()
                .id(orderPo.getId())
                .createdAt(orderPo.getCreatedAt())
                .destinationFrom(LocationMapper.mapLocationPoToTo(destinationFrom))
                .destinationTo(LocationMapper.mapLocationPoToTo(destinationTo))
                .packages(packageTos)
                .salary(orderPo.getSalary() != null ? orderPo.getSalary().toString() : null)
                .requireTransportWithClient(orderPo.isRequireTransportWithClient())
                .weight(PackagesWeightCounterHelper.sumPackagesWeights(packageTos).toString())
                .weightUnit(orderPo.getWeightUnit())
                .build();

    }
}
