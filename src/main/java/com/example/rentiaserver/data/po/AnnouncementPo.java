package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.data.enums.AnnouncementType;
import com.example.rentiaserver.delivery.po.DeliveryPo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "ANNOUNCEMENTS")
public class AnnouncementPo extends BaseEntityPo {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "INITIAL_LOCATION_ID", nullable = false)
    private LocationPo initialLocationPo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FINAL_LOCATION_ID", nullable = false)
    private LocationPo finalLocationPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private UserPo authorPo;

    @OneToMany(mappedBy = "announcementPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DeliveryPo> deliveryPos;

    @OneToMany(mappedBy = "announcementPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AnnouncementPackagePo> packagesPos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnnouncementType announcementType;

    @ManyToMany(mappedBy = "relatedAnnouncements")
    Set<UserPo> associatedUserPos;

    @Column(nullable = false)
    private BigDecimal amount;

    public AnnouncementPo(LocationPo initialLocationPo, LocationPo finalLocationPo, UserPo authorPo, AnnouncementType announcementType, BigDecimal amount) {
        this.initialLocationPo = initialLocationPo;
        this.finalLocationPo = finalLocationPo;
        this.authorPo = authorPo;
        this.announcementType = announcementType;
        this.amount = amount;
    }

    public AnnouncementPo() {}

    public LocationPo getInitialLocationPo() {
        return initialLocationPo;
    }

    public void setInitialLocationPo(LocationPo initialLocationPo) {
        this.initialLocationPo = initialLocationPo;
    }

    public LocationPo getFinalLocationPo() {
        return finalLocationPo;
    }

    public void setFinalLocationPo(LocationPo finalLocationPo) {
        this.finalLocationPo = finalLocationPo;
    }

    public UserPo getAuthorPo() {
        return authorPo;
    }

    public void setAuthorPo(UserPo authorPo) {
        this.authorPo = authorPo;
    }

    public Set<DeliveryPo> getDeliveryPos() {
        return deliveryPos;
    }

    public void setDeliveryPos(Set<DeliveryPo> deliveryPos) {
        this.deliveryPos = deliveryPos;
    }

    public Set<AnnouncementPackagePo> getPackagesPos() {
        return packagesPos;
    }

    public void setPackagesPos(Set<AnnouncementPackagePo> packagesPos) {
        this.packagesPos = packagesPos;
    }

    public AnnouncementType getAnnouncementType() {
        return announcementType;
    }

    public void setAnnouncementType(AnnouncementType announcementType) {
        this.announcementType = announcementType;
    }

    public Set<UserPo> getAssociatedUserPos() {
        return associatedUserPos;
    }

    public void setAssociatedUserPos(Set<UserPo> associatedUserPos) {
        this.associatedUserPos = associatedUserPos;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

