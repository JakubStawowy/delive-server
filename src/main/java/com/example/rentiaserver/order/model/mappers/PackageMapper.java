package com.example.rentiaserver.order.model.mappers;

import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.order.model.po.PackagePo;
import com.example.rentiaserver.order.model.to.PackageTo;

import java.math.BigDecimal;

public class PackageMapper {

    public static PackagePo mapPackageToToPo(PackageTo packageTo, OrderPo orderPo) {
        return new PackagePo(
                new BigDecimal(packageTo.getLength()),
                packageTo.getLengthUnit(),
                new BigDecimal(packageTo.getWidth()),
                packageTo.getWidthUnit(),
                new BigDecimal(packageTo.getHeight()),
                packageTo.getHeightUnit(),
                packageTo.getWeight() != null ? new BigDecimal(packageTo.getWeight()) : null,
                orderPo
        );
    }

    public static PackageTo mapPackagePoToTo(PackagePo packagePo) {

        return PackageTo.builder()
                .height(String.valueOf(packagePo.getHeight()))
                .length(String.valueOf(packagePo.getLength()))
                .width(String.valueOf(packagePo.getWidth()))
                .weight(String.valueOf(packagePo.getWeight()))
                .heightUnit(packagePo.getHeightUnit())
                .lengthUnit(packagePo.getLengthUnit())
                .widthUnit(packagePo.getWidthUnit())
                .build();
    }
}
