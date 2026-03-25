package models;
import java.math.BigDecimal;

public class PointRequest {
    private Integer x;
    private BigDecimal y;
    private Integer r;

    public PointRequest() {}

    public PointRequest(Integer x, BigDecimal y, Integer r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Integer getX() {
        return this.x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return this.y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public Integer getR() {
        return this.r;
    }

    public void setR(Integer r) {
        this.r = r;
    }
}
