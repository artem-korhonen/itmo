package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

// import jakarta.enterprise.context.SessionScoped;
// import jakarta.inject.Named;
import utils.AreaCheck;


// @Named("pageManagerBean")
// @ApplicationScoped
public class PageManagerBean implements Serializable {
    private ResultsManagerBean resultsManagerBean;

    private Integer x;
    private BigDecimal y;
    private Integer r;
    private boolean hit;

    public PageManagerBean() {
    }

    public ResultsManagerBean getResultsManagerBean() {
        return resultsManagerBean;
    }

    public Integer getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public Integer getR() {
        return r;
    }

    public boolean isHit() {
        return hit;
    }

    public void setResultsManagerBean(ResultsManagerBean resultsManagerBean) {
        this.resultsManagerBean = resultsManagerBean;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void addPoint() {
        LocalDateTime startTime = LocalDateTime.now();

        boolean newHit = AreaCheck.checkArea(
                    BigDecimal.valueOf(this.x != null ? this.x : 0),
                    this.y,
                    BigDecimal.valueOf(this.r != null ? this.r : 0));
        
        LocalDateTime endTime = LocalDateTime.now();

        this.hit = newHit;
        Point point = new Point(x, y, r, newHit, endTime, Duration.between(startTime, endTime).toNanos());
        resultsManagerBean.addPoint(point);
    }
}
