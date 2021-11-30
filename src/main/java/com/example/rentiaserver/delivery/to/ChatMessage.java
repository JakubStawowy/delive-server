package com.example.rentiaserver.delivery.to;

public class ChatMessage {

    private String value;
    private String token;
    private String user;

    public ChatMessage(String value) {
        this.value = value;
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
