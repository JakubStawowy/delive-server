package com.example.rentiaserver.data.po;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "normal_announcement")
public class NormalAnnouncementPo extends AnnouncementPo {

    @NotNull
    private BigDecimal packageLength;

    @NotNull
    private BigDecimal packageWidth;

    @NotNull
    private BigDecimal packageHeight;

    public NormalAnnouncementPo(DestinationPo destinationFrom, DestinationPo destinationTo, UserPo author, @NotNull BigDecimal packageLength, @NotNull BigDecimal packageWidth, @NotNull BigDecimal packageHeight) {
        super(destinationFrom, destinationTo, author);
        this.packageLength = packageLength;
        this.packageWidth = packageWidth;
        this.packageHeight = packageHeight;
    }

    public NormalAnnouncementPo() {}

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
