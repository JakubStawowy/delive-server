package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.api.BaseEntityPo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;

import java.math.BigDecimal;

@Entity
@Table(name = "TB_PACKAGE")
@Inheritance(strategy = InheritanceType.JOINED)
public class PackagePo extends BaseEntityPo {

    @Column(name = "LENGTH", nullable = false)
    private BigDecimal packageLength;

    @Column(name="LENGTH_UNIT", nullable = false)
    private String lengthUnit;

    @Column(name = "WIDTH", nullable = false)
    private BigDecimal packageWidth;

    @Column(name = "WIDTH_UNIT", nullable = false)
    private String widthUnit;

    @Column(name = "HEIGHT", nullable = false)
    private BigDecimal packageHeight;

    @Column(name = "HEIGHT_UNIT", nullable = false)
    private String heightUnit;

    @Column(name = "WEIGHT", nullable = false)
    private BigDecimal weight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private OrderPo orderPo;

    public PackagePo(BigDecimal packageLength, String lengthUnit, BigDecimal packageWidth, String widthUnit,
                     BigDecimal packageHeight, String heightUnit, BigDecimal weight,
                     OrderPo orderPo) {
        this.packageLength = packageLength;
        this.lengthUnit = lengthUnit;
        this.packageWidth = packageWidth;
        this.widthUnit = widthUnit;
        this.packageHeight = packageHeight;
        this.heightUnit = heightUnit;
        this.weight = weight;
        this.orderPo = orderPo;
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

    public OrderPo getOrderPo() {
        return orderPo;
    }

    public void setOrderPo(OrderPo orderPo) {
        this.orderPo = orderPo;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getLengthUnit() {
        return lengthUnit;
    }

    public void setLengthUnit(String lengthUnit) {
        this.lengthUnit = lengthUnit;
    }

    public String getWidthUnit() {
        return widthUnit;
    }

    public void setWidthUnit(String widthUnit) {
        this.widthUnit = widthUnit;
    }

    public String getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(String heightUnit) {
        this.heightUnit = heightUnit;
    }

}
