package com.example.rentiaserver.geolocation.model.po;

import com.example.rentiaserver.base.model.po.BaseEntityPo;
import com.example.rentiaserver.order.model.po.OrderPo;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.Column;

@Entity
@Table(name = "TB_LOCATION")
public class LocationPo extends BaseEntityPo {

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Nullable
    private String address;

    @OneToOne(mappedBy = "initialLocationPo")
    private OrderPo orderFromPo;

    @OneToOne(mappedBy = "finalLocationPo")
    private OrderPo orderToPo;

    public LocationPo(Double latitude, Double longitude, @Nullable String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public LocationPo() {}

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    public OrderPo getOrderFromPo() {
        return orderFromPo;
    }

    public void setOrderFromPo(OrderPo orderFromPo) {
        this.orderFromPo = orderFromPo;
    }

    public OrderPo getOrderToPo() {
        return orderToPo;
    }

    public void setOrderToPo(OrderPo orderToPo) {
        this.orderToPo = orderToPo;
    }
}
