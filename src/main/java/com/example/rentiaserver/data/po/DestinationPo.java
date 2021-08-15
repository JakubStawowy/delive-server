package com.example.rentiaserver.data.po;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class DestinationPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;

    @Nullable
    private String address;


    @OneToOne(mappedBy = "destinationFrom")
    private AnnouncementPo announcementFrom;

    @OneToOne(mappedBy = "destinationTo")
    private AnnouncementPo announcementTo;

    public DestinationPo(@NotNull BigDecimal latitude, @NotNull BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public DestinationPo() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
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
