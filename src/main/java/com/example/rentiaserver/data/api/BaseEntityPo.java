package com.example.rentiaserver.data.api;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntityPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "is_editable", nullable = false)
    protected boolean isEditable;

    @Column(name = "created_at", updatable = false)
    protected Date createdAt;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    @PrePersist
    public void init() {
        createdAt = new Date(System.currentTimeMillis());
        isEditable = true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
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
}
