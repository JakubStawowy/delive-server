package com.example.rentiaserver.data.to;

import java.io.Serializable;

public class PackageTo implements Serializable {

    private final String packageLength;
    private final String packageWidth;
    private final String packageHeight;

    public PackageTo(String packageLength, String packageWidth, String packageHeight) {
        this.packageLength = packageLength;
        this.packageWidth = packageWidth;
        this.packageHeight = packageHeight;
    }

    public String getPackageLength() {
        return packageLength;
    }

    public String getPackageWidth() {
        return packageWidth;
    }

    public String getPackageHeight() {
        return packageHeight;
    }
}
