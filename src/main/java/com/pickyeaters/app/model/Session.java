package com.pickyeaters.app.model;

public class Session {
    private String token;

    public Session() {
        this.token = "";
    }
    public Session(String token) {
        this.token = token;
    }

    public boolean isValid() {
        return this.token != null && !this.token.isEmpty();
    }
}
