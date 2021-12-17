package com.example.rentiaserver.delivery.to;

import java.io.Serializable;

public class ChatMessage implements Serializable {

    private String value;
    private String token;
    private String user;

    public ChatMessage(String value, String token) {
        this.value = value;
        this.token = token;
    }

    public ChatMessage() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
