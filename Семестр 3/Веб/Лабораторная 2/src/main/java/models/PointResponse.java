package models;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

public class PointResponse {
    private int x;
    private BigDecimal y;
    private int r;
    private boolean test;
    private String currentTime;
    private long requestTime;

    public PointResponse(int x, BigDecimal y, int r, boolean test, Instant currentTime, Duration duration) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.test = test;
        this.currentTime = currentTime.toString();
        this.requestTime = duration.toNanos();
    }

    public int getX() {
        return this.x;
    }

    public BigDecimal getY() {
        return this.y;
    }

    public int getR() {
        return this.r;
    }

    public boolean getTest() {
        return this.test;
    }

    public String getCurrentTime() {
        return this.currentTime;
    }

    public long getRequestTime() {
        return this.requestTime;
    }
}
