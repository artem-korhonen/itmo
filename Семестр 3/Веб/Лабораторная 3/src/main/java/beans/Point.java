package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "points")
public class Point implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "x")
    private Integer x;

    @Column(name = "y", precision = 20, scale = 8)
    private BigDecimal y;

    @Column(name = "r")
    private Integer r;

    @Column(name = "hit")
    private boolean hit;

    @Column(name = "current_time_value")
    private LocalDateTime currentTime;

    @Column(name = "request_time")
    private long requestTime;

    public Point() {
    }

    public Point(Integer x, BigDecimal y, Integer r, boolean hit, LocalDateTime currentTime, long requestTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.currentTime = currentTime;
        this.requestTime = requestTime;
    }

    public Long getId() {
        return id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public double getRequestTimeMs() {
        return requestTime / 1_000_000.0;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    @Override
    public String toString() {
        return "Point{id=" + id + ", x=" + x + ", y=" + y + ", r=" + r + ", hit=" + hit +
                ", currentTime=" + currentTime + ", requestTime=" + requestTime + '}';
    }
}
