package ui;

import data.City;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.*;

public class TableManager {
    private final ObservableList<City> cityData = FXCollections.observableArrayList();

    public TableManager(TableView<City> tableView,
                            TableColumn<City, Integer> idColumn,
                            TableColumn<City, String> usernameColumn,
                            TableColumn<City, String> nameColumn,
                            TableColumn<City, Double> coordXColumn,
                            TableColumn<City, Double> coordYColumn,
                            TableColumn<City, Integer> areaColumn,
                            TableColumn<City, Integer> populationColumn,
                            TableColumn<City, Double> meterAboveSeaLevelColumn,
                            TableColumn<City, Long> populationDensityColumn,
                            TableColumn<City, String> governmentColumn,
                            TableColumn<City, String> governorNameColumn,
                            TableColumn<City, Integer> governorAgeColumn,
                            TableColumn<City, Double> governorHeightColumn,
                            TableColumn<City, String> governorBirthdayColumn) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordXColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getCoordinates().getX()).asObject());
        coordYColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getCoordinates().getY()).asObject());
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        populationColumn.setCellValueFactory(new PropertyValueFactory<>("population"));
        meterAboveSeaLevelColumn.setCellValueFactory(new PropertyValueFactory<>("metersAboveSeaLevel"));
        populationDensityColumn.setCellValueFactory(new PropertyValueFactory<>("populationDensity"));
        governmentColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGovernment().toString()));
        governorNameColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGovernor().getName()));
        governorAgeColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getGovernor().getAge()));
        governorHeightColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getGovernor().getHeight()).asObject());
        governorBirthdayColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGovernor().getBirthday().toString()));

        tableView.setItems(cityData);
    }

    private int findIndexById(int id) {
        for (int i = 0; i < cityData.size(); i++) {
            if (cityData.get(i).getId() == id) return i;
        }
        return -1;
    }

    public List<City> getRemovedCities(Hashtable<Integer, City> newCitiesMap) {
        List<City> removedCities = new ArrayList<>();

        for (City oldCity : new ArrayList<>(cityData)) {
            if (!newCitiesMap.containsKey(oldCity.getId())) {
                removedCities.add(oldCity);
            }
        }

        return removedCities;
    }

    public List<City> getNewCities(Hashtable<Integer, City> newCitiesMap) {
        List<City> newCities = new ArrayList<>();

        for (City city : newCitiesMap.values()) {
            int index = findIndexById(city.getId());
            if (index == -1) {
                newCities.add(city);
            }
        }

        return newCities;
    }

    public List<City> getUpdatedCities(Hashtable<Integer, City> newCitiesMap) {
        List<City> updatedCities = new ArrayList<>();

        for (City city : newCitiesMap.values()) {
            int index = findIndexById(city.getId());
            if (index != -1 && !cityData.get(index).equals(city)) {
                updatedCities.add(city);
            }
        }

        return updatedCities;
    }

    public void updateTable(Hashtable<Integer, City> newCitiesMap) {
        List<City> removedCities = getRemovedCities(newCitiesMap);
        List<City> newCities = getNewCities(newCitiesMap);
        List<City> updatedCities = getUpdatedCities(newCitiesMap);

        this.cityData.removeAll(removedCities);

        for (City city : updatedCities) {
            int index = findIndexById(city.getId());
            this.cityData.set(index, city);
        }

        this.cityData.addAll(newCities);
    }

    public ObservableList<City> getCityData() {
        return cityData;
    }
}