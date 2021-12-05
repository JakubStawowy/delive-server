package com.example.rentiaserver.data.to;

import com.example.rentiaserver.data.api.BaseEntityTo;

public class PackageTo extends BaseEntityTo {

    private final String packageLength;
    private final String lengthUnit;
    private final String packageWidth;
    private final String widthUnit;
    private final String packageHeight;
    private final String heightUnit;
    private final String packageWeight;

    public PackageTo(Long id, String createdAt, String packageLength, String lengthUnit, String packageWidth, String widthUnit, String packageHeight, String heightUnit, String packageWeight) {
        super(id, createdAt);
        this.packageLength = packageLength;
        this.lengthUnit = lengthUnit;
        this.packageWidth = packageWidth;
        this.widthUnit = widthUnit;
        this.packageHeight = packageHeight;
        this.heightUnit = heightUnit;
        this.packageWeight = packageWeight;
    }

    public String getPackageLength() {
        return packageLength;
    }

    public String getLengthUnit() {
        return lengthUnit;
    }

    public String getPackageWidth() {
        return packageWidth;
    }

    public String getWidthUnit() {
        return widthUnit;
    }

    public String getPackageHeight() {
        return packageHeight;
    }

    public String getHeightUnit() {
        return heightUnit;
    }

    public String getPackageWeight() {
        return packageWeight;
    }
}
