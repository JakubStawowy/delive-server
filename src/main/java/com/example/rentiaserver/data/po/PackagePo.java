package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.api.BaseEntityPo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "PACKAGES")
@Inheritance(strategy = InheritanceType.JOINED)
public class PackagePo extends BaseEntityPo {

    @Column(name = "PACKAGE_LENGTH", nullable = false)
    private BigDecimal packageLength;

    @Column(name = "PACKAGE_WIDTH", nullable = false)
    private BigDecimal packageWidth;

    @Column(name = "PACKAGE_HEIGHT", nullable = false)
    private BigDecimal packageHeight;

    public PackagePo(@NotNull BigDecimal packageLength, @NotNull BigDecimal packageWidth, @NotNull BigDecimal packageHeight) {
        this.packageLength = packageLength;
        this.packageWidth = packageWidth;
        this.packageHeight = packageHeight;
    }

    public PackagePo() {}

    public BigDecimal getPackageLength() {
        return packageLength;
    }

    public void setPackageLength(BigDecimal packageLength) {
        this.packageLength = packageLength;
    }

    public BigDecimal getPackageWidth() {
        return packageWidth;
    }

    public void setPackageWidth(BigDecimal packageWidth) {
        this.packageWidth = packageWidth;
    }

    public BigDecimal getPackageHeight() {
        return packageHeight;
    }

    public void setPackageHeight(BigDecimal packageHeight) {
        this.packageHeight = packageHeight;
    }

}
