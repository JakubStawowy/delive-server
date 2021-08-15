package com.example.rentiaserver.security.to;

import com.example.rentiaserver.security.enums.UserRoles;

import java.io.Serializable;

public class LoginTo implements Serializable {

    private final Long userId;
    private final String token;
    private final UserRoles role;

    public LoginTo(Long userId, String token, UserRoles role) {
        this.userId = userId;
        this.token = token;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public UserRoles getRole() {
        return role;
    }
}
