package com.example.rentiaserver.data.po;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "announcement_package")
public class AnnouncementPackagePo extends PackagePo {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "announcement_id")
    private AnnouncementPo announcement;

    public AnnouncementPackagePo(@NotNull BigDecimal packageLength, @NotNull BigDecimal packageWidth, @NotNull BigDecimal packageHeight, AnnouncementPo announcement) {
        super(packageLength, packageWidth, packageHeight);
        this.announcement = announcement;
    }

    public AnnouncementPackagePo() {
    }

    public AnnouncementPo getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(AnnouncementPo announcement) {
        this.announcement = announcement;
    }
}
