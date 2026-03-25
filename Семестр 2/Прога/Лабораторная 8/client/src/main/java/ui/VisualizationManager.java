package ui;

import data.City;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class VisualizationManager {
    private final Map<String, Color> userColors = new HashMap<>();
    private final Random rand = new Random();

    private final double scaleX = 50;
    private final double scaleY = 50;

    private final double offsetX = 159;
    private final double offsetY = 113;

    public void drawCities(Hashtable<Integer, City> cities, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (City city : cities.values()) {
            String user = city.getUsername();
            userColors.putIfAbsent(user, Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));

            double centerX = getNewX(city.getCoordinates().getX());
            double centerY = getNewY(city.getCoordinates().getY());
            double diameter = getDiameter(city.getArea());

            double topLeftX = centerX - diameter / 2;
            double topLeftY = centerY - diameter / 2;

            gc.setFill(userColors.get(user));
            gc.fillOval(topLeftX, topLeftY, diameter, diameter);
        }
    }

    public City canvasClick(Hashtable<Integer, City> cities, double clickX, double clickY) {
        double minDiameter = 1000000000000d;
        City finalCity = null;

        for (City city : cities.values()) {
            double centerX = getNewX(city.getCoordinates().getX());
            double centerY = getNewY(city.getCoordinates().getY());
            double diameter = getDiameter(city.getArea());

            double x = clickX - centerX;
            double y = clickY - centerY;
            double radius = diameter / 2;

            if (x * x + y * y <= radius * radius && diameter < minDiameter) {
                minDiameter = diameter;
                finalCity = city;
            }
        }

        if (finalCity != null) {
            return finalCity;
        } else {
            return null;
        }
    }

    private double getNewX(float x) {
        return x * scaleX + offsetX;
    }

    private double getNewY(long y) {
        return offsetY - y * scaleY;
    }

    private double getDiameter(int area) {
        double radius = Math.sqrt(area / Math.PI);
        double diameter = 2 * radius;
        return diameter * 10;
    }
}
