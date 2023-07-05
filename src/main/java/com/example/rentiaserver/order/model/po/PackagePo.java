package com.example.rentiaserver.order.model.po;

import com.example.rentiaserver.base.model.po.BaseEntityPo;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_PACKAGE")
@Inheritance(strategy = InheritanceType.JOINED)
public class PackagePo extends BaseEntityPo {

    @Column(name = "LENGTH", nullable = false)
    private BigDecimal length;

    @Column(name="LENGTH_UNIT", nullable = false)
    private String lengthUnit;

    @Column(name = "WIDTH", nullable = false)
    private BigDecimal width;

    @Column(name = "WIDTH_UNIT", nullable = false)
    private String widthUnit;

    @Column(name = "HEIGHT", nullable = false)
    private BigDecimal height;

    @Column(name = "HEIGHT_UNIT", nullable = false)
    private String heightUnit;

    @Column(name = "WEIGHT", nullable = false)
    private BigDecimal weight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private OrderPo orderPo;

    public PackagePo(BigDecimal packageLength,
                     String lengthUnit,
                     BigDecimal packageWidth,
                     String widthUnit,
                     BigDecimal packageHeight,
                     String heightUnit,
                     BigDecimal weight,
                     OrderPo orderPo) {
        this.length = packageLength;
        this.lengthUnit = lengthUnit;
        this.width = packageWidth;
        this.widthUnit = widthUnit;
        this.height = packageHeight;
        this.heightUnit = heightUnit;
        this.weight = weight;
        this.orderPo = orderPo;
    }

    public PackagePo() {}

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
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
