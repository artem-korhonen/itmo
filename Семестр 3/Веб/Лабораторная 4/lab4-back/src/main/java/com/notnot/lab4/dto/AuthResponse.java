package com.notnot.lab4.dto;

public class AuthResponse {
    private String token;
    private String error;

    public AuthResponse(String token, String error) {
        this.token = token;
        this.error = error;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
