package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.geolocation.po.LocationPo;

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
    private Set<PackagePo> packagesPos;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean requireTransportWithClient;

    @OneToMany(mappedBy = "announcementPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeedbackPo> feedbackPos;

    public AnnouncementPo(LocationPo initialLocationPo, LocationPo finalLocationPo, UserPo authorPo, BigDecimal amount, boolean requireTransportWithClient) {
        this.initialLocationPo = initialLocationPo;
        this.finalLocationPo = finalLocationPo;
        this.authorPo = authorPo;
        this.amount = amount;
        this.requireTransportWithClient = requireTransportWithClient;
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

    public Set<PackagePo> getPackagesPos() {
        return packagesPos;
    }

    public void setPackagesPos(Set<PackagePo> packagesPos) {
        this.packagesPos = packagesPos;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isRequireTransportWithClient() {
        return requireTransportWithClient;
    }

    public void setRequireTransportWithClient(boolean requireTransportWithClient) {
        this.requireTransportWithClient = requireTransportWithClient;
    }

    public Set<FeedbackPo> getFeedbackPos() {
        return feedbackPos;
    }

    public void setFeedbackPos(Set<FeedbackPo> feedbackPos) {
        this.feedbackPos = feedbackPos;
    }
}

