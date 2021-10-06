package com.example.rentiaserver.data.api;

import java.io.Serializable;

public abstract class BaseEntityTo implements Serializable {

    private final Long id;
    private final String createdAt;

    public BaseEntityTo(Long id, String createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}

