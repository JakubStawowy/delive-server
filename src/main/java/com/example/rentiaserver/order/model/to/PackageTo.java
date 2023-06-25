package com.example.rentiaserver.order.model.to;

import com.example.rentiaserver.base.model.to.BaseEntityTo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PackageTo extends BaseEntityTo {
    private String length;
    private String lengthUnit;
    private String width;
    private String widthUnit;
    private String height;
    private String heightUnit;
    private String weight;
}
