package com.example.rentiaserver.data.po;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ANNOUNCEMENT_PACKAGES")
public class AnnouncementPackagePo extends PackagePo {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ANNOUNCEMENT_ID", nullable = false)
    private AnnouncementPo announcementPo;

    public AnnouncementPackagePo(BigDecimal packageLength, BigDecimal packageWidth, BigDecimal packageHeight, AnnouncementPo announcementPo) {
        super(packageLength, packageWidth, packageHeight);
        this.announcementPo = announcementPo;
    }

    public AnnouncementPackagePo() {
    }

    public AnnouncementPo getAnnouncementPo() {
        return announcementPo;
    }

    public void setAnnouncementPo(AnnouncementPo announcementPo) {
        this.announcementPo = announcementPo;
    }
}
