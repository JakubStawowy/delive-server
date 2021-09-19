package com.example.rentiaserver.security.to;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class ResponseTo implements Serializable {

    private final boolean operationSuccess;
    private String message;
    private final HttpStatus status;

    public ResponseTo(boolean operationSuccess, String message, HttpStatus status) {
        this.operationSuccess = operationSuccess;
        this.message = message;
        this.status = status;
    }

    public ResponseTo(boolean operationSuccess, HttpStatus status) {
        this.operationSuccess = operationSuccess;
        this.status = status;
    }

    public boolean isOperationSuccess() {
        return operationSuccess;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
