package com.example.rentiaserver.data.po;

import com.example.rentiaserver.delivery.po.DeliveryPo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "announcements")
public class AnnouncementPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_from_id")
    private DestinationPo destinationFrom;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_to_id")
    private DestinationPo destinationTo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id")
    private UserPo author;

    @JoinColumn(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "announcement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DeliveryPo> commissions;

    @OneToMany(mappedBy = "announcement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AnnouncementPackagePo> packages;

    @NotNull
    private LocalDateTime date;

    public AnnouncementPo(DestinationPo destinationFrom, DestinationPo destinationTo, UserPo author) {
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.author = author;
    }

    public AnnouncementPo() {}

    @PrePersist
    public void init() {
        createdAt = new Date(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DestinationPo getDestinationFrom() {
        return destinationFrom;
    }

    public void setDestinationFrom(DestinationPo destinationFrom) {
        this.destinationFrom = destinationFrom;
    }

    public DestinationPo getDestinationTo() {
        return destinationTo;
    }

    public void setDestinationTo(DestinationPo destinationTo) {
        this.destinationTo = destinationTo;
    }

    public UserPo getAuthor() {
        return author;
    }

    public void setAuthor(UserPo author) {
        this.author = author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<DeliveryPo> getCommissions() {
        return commissions;
    }

    public void setCommissions(Set<DeliveryPo> commissions) {
        this.commissions = commissions;
    }

    public Set<AnnouncementPackagePo> getPackages() {
        return packages;
    }

    public void setPackages(Set<AnnouncementPackagePo> packages) {
        this.packages = packages;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

