package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.api.BaseEntityPo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "PACKAGES")
@Inheritance(strategy = InheritanceType.JOINED)
public class PackagePo extends BaseEntityPo {

    @Column(name = "LENGTH", nullable = false)
    private BigDecimal packageLength;

    @Column(name = "WIDTH", nullable = false)
    private BigDecimal packageWidth;

    @Column(name = "HEIGHT", nullable = false)
    private BigDecimal packageHeight;

    @Column(name = "WEIGHT")
    private BigDecimal weight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ANNOUNCEMENT_ID", nullable = false)
    private AnnouncementPo announcementPo;

    public PackagePo(BigDecimal packageLength, BigDecimal packageWidth, BigDecimal packageHeight, BigDecimal weight, AnnouncementPo announcementPo) {
        this.packageLength = packageLength;
        this.packageWidth = packageWidth;
        this.packageHeight = packageHeight;
        this.weight = weight;
        this.announcementPo = announcementPo;
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

    public AnnouncementPo getAnnouncementPo() {
        return announcementPo;
    }

    public void setAnnouncementPo(AnnouncementPo announcementPo) {
        this.announcementPo = announcementPo;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
