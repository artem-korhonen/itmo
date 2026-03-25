package com.notnot.lab4.dto;

public class PointRequest {
    private int x;
    private Double y;
    private int r;
    private String token;

    public PointRequest(int x, Double y, int r, String token) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.token = token;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Double getY() {
        return this.y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public int getR() {
        return this.r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
