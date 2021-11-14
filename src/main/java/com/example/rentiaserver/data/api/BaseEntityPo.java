package com.example.rentiaserver.data.api;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntityPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "IS_ARCHIVED", nullable = false)
    protected boolean isArchived;

    @Column(name = "CREATED_AT", updatable = false)
    protected Date createdAt;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    @PrePersist
    public void init() {
        createdAt = new Date(System.currentTimeMillis());
        isArchived = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}
