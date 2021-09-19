package com.example.rentiaserver.security.to;

import com.example.rentiaserver.security.enums.UserRoles;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class LoginResponseTo extends ResponseTo {

    private final Long userId;
    private final String token;
    private final UserRoles role;
    private final boolean loginSuccess;

    public LoginResponseTo(boolean operationSuccess, String message, HttpStatus status, Long userId, String token, UserRoles role, boolean loginSuccess) {
        super(operationSuccess, message, status);
        this.userId = userId;
        this.token = token;
        this.role = role;
        this.loginSuccess = loginSuccess;
    }

    public LoginResponseTo(boolean operationSuccess, HttpStatus status, Long userId, String token, UserRoles role, boolean loginSuccess) {
        super(operationSuccess, status);
        this.userId = userId;
        this.token = token;
        this.role = role;
        this.loginSuccess = loginSuccess;
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

    public boolean isLoginSuccess() {
        return loginSuccess;
    }
}
