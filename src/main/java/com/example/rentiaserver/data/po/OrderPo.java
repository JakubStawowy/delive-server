package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.geolocation.po.LocationPo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "TB_ORDER")
public class OrderPo extends BaseEntityPo {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "INITIAL_LOCATION_ID", nullable = false)
    private LocationPo initialLocationPo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FINAL_LOCATION_ID", nullable = false)
    private LocationPo finalLocationPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private UserPo authorPo;

    @OneToMany(mappedBy = "orderPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DeliveryPo> deliveryPos;

    @OneToMany(mappedBy = "orderPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PackagePo> packagesPos;

    @Column(name = "WEIGHT_UNIT", nullable = false)
    private String weightUnit;

    @Column(nullable = false)
    private BigDecimal salary;

    @Column(nullable = false)
    private boolean requireTransportWithClient;

    @OneToMany(mappedBy = "orderPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeedbackPo> feedbackPos;

    public OrderPo(LocationPo initialLocationPo, LocationPo finalLocationPo, UserPo authorPo, BigDecimal salary, boolean requireTransportWithClient, String weightUnit) {
        this.initialLocationPo = initialLocationPo;
        this.finalLocationPo = finalLocationPo;
        this.authorPo = authorPo;
        this.salary = salary;
        this.requireTransportWithClient = requireTransportWithClient;
        this.weightUnit = weightUnit;
    }

    public OrderPo() {}

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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
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

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }
}

