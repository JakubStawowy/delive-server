package com.example.rentiaserver.data.po;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "announcements")
@Inheritance(strategy = InheritanceType.JOINED)
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
}

