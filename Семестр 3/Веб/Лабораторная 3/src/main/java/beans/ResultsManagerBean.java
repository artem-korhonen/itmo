package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// import jakarta.enterprise.context.ApplicationScoped;
// import jakarta.inject.Named;
import utils.DatabaseManager;


// @Named("resultsManagerBean")
// @ApplicationScoped
public class ResultsManagerBean implements Serializable {
    private final DatabaseManager dbManager;
    private List<Point> points;


    public ResultsManagerBean() {
        dbManager = new DatabaseManager();
        points = dbManager.getPoints();
        if (points == null) {
            points = new ArrayList<>();
        }
    }

    public List<Point> getPoints() {
        List<Point> copyPoints = new ArrayList<>(this.points);
        Collections.reverse(copyPoints);
        return copyPoints;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void addPoint(Point point) {
        dbManager.savePoint(point);
        this.points.add(point);
    }

    public void updatePoints() {
        this.points = dbManager.getPoints();
    }
}