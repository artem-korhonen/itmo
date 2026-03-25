package com.notnot.lab4.dto;

import java.util.List;

import com.notnot.lab4.entities.Point;

public class PointResponse {
    private boolean hit;
    private List<Point> points;
    private String error;

    public PointResponse(boolean hit, List<Point> points) {
        this.hit = hit;
        this.points = points;
    }

    public PointResponse(String error) {
        this.error = error;
    }

    public boolean isHit() {
        return this.hit;
    }

    public boolean getHit() {
        return this.hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public List<Point> getPoints() {
        return this.points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
