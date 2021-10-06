package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "destination")
public class DestinationPo extends BaseEntityPo {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @Nullable
    private String address;

    @Nullable
    private String locality;

    @Nullable
    private String country;

    @OneToOne(mappedBy = "destinationFrom")
    private AnnouncementPo announcementFrom;

    @OneToOne(mappedBy = "destinationTo")
    private AnnouncementPo announcementTo;

    public DestinationPo(@NotNull Double latitude, @NotNull Double longitude, @Nullable String address, @Nullable String locality, @Nullable String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.locality = locality;
        this.country = country;
    }

    public DestinationPo() {}

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

    @Nullable
    public String getLocality() {
        return locality;
    }

    public void setLocality(@Nullable String locality) {
        this.locality = locality;
    }

    @Nullable
    public String getCountry() {
        return country;
    }

    public void setCountry(@Nullable String country) {
        this.country = country;
    }

    public AnnouncementPo getAnnouncementFrom() {
        return announcementFrom;
    }

    public void setAnnouncementFrom(AnnouncementPo announcementFrom) {
        this.announcementFrom = announcementFrom;
    }

    public AnnouncementPo getAnnouncementTo() {
        return announcementTo;
    }

    public void setAnnouncementTo(AnnouncementPo announcementTo) {
        this.announcementTo = announcementTo;
    }
}
