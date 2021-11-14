package com.example.rentiaserver.maps.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.data.po.AnnouncementPo;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "LOCATIONS")
public class LocationPo extends BaseEntityPo {

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Nullable
    private String address;

    @OneToOne(mappedBy = "initialLocationPo")
    private AnnouncementPo announcementFromPo;

    @OneToOne(mappedBy = "finalLocationPo")
    private AnnouncementPo announcementToPo;

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

    public AnnouncementPo getAnnouncementFromPo() {
        return announcementFromPo;
    }

    public void setAnnouncementFromPo(AnnouncementPo announcementFromPo) {
        this.announcementFromPo = announcementFromPo;
    }

    public AnnouncementPo getAnnouncementToPo() {
        return announcementToPo;
    }

    public void setAnnouncementToPo(AnnouncementPo announcementToPo) {
        this.announcementToPo = announcementToPo;
    }
}
