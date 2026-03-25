package ui;

import java.time.LocalDate;
import java.util.function.Predicate;

import data.City;
import data.Coordinates;
import data.Government;
import data.Human;
import exceptions.StringConvertException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.LocalizeManager;
import main.StringConverter;

public class CityInputController {
    private String username;
    private City city;
    private Stage stage;

    @FXML
    private TextField idInput;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField coordXInput;
    @FXML
    private TextField coordYInput;
    @FXML
    private TextField areaInput;
    @FXML
    private TextField populationInput;
    @FXML
    private TextField metersInput;
    @FXML
    private TextField timezoneInput;
    @FXML
    private TextField populationDensityInput;
    @FXML
    private TextField governmentInput;
    @FXML
    private TextField governorNameInput;
    @FXML
    private TextField governorAgeInput;
    @FXML
    private TextField governorHeightInput;
    @FXML
    private TextField governorBirthdayInput;

    @FXML
    private Button ClearButton;
    @FXML
    private Button InputButton;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public City getCity() {
        return this.city;
    }

    @FXML
    private void inputButtonClick() {
        try {
            Integer id = validateAndConvert(idInput.getText(), Integer.class, false, i -> i > 0, "Id must be > 0");
            String name = validateAndConvert(nameInput.getText(), String.class, false, s -> !s.isBlank(), "Name cannot be blank");
            Float x = validateAndConvert(coordXInput.getText(), Float.class, false, f -> f <= 774, "X must be <= 774");
            Long y = validateAndConvert(coordYInput.getText(), Long.class, false, l -> l > -497, "Y must be > -497");
            Integer area = validateAndConvert(areaInput.getText(), Integer.class, false, i -> i > 0, "Area must be > 0");
            Integer population = validateAndConvert(populationInput.getText(), Integer.class, false, i -> i > 0, "Population must be > 0");
            Double metersAboveSeaLevel = validateAndConvert(metersInput.getText(), Double.class, false, d -> true, ""); // нет ограничений
            Float timezone = validateAndConvert(timezoneInput.getText(), Float.class, false, f -> f > -13 && f <= 15, "Timezone must be > -13 and <= 15");
            Long populationDensity = validateAndConvert(populationDensityInput.getText(), Long.class, false, l -> l > 0, "Density must be > 0");
            Government government = validateAndConvert(governmentInput.getText(), Government.class, false, g -> true, ""); // валидируется в конвертере
            String governorName = validateAndConvert(governorNameInput.getText(), String.class, false, s -> !s.isBlank(), "Governor name cannot be blank");
            Integer governorAge = validateAndConvert(governorAgeInput.getText(), Integer.class, false, i -> i > 0, "Governor age must be > 0");
            Integer governorHeight = validateAndConvert(governorHeightInput.getText(), Integer.class, false, i -> i > 0, "Governor height must be > 0");
            LocalDate governorBirthday = validateAndConvert(governorBirthdayInput.getText(), LocalDate.class, false, d -> true, "");

            this.city = new City(id, this.username, name, new Coordinates(x, y), area, population, metersAboveSeaLevel, timezone, populationDensity, government, new Human(governorName, governorAge, governorHeight, governorBirthday));

            this.stage.close();
        } catch (StringConvertException e) {
            showErrorDialog(e.getMessage());
        }
    }

    @FXML
    private void clearButtonClick() {
        idInput.clear();
        nameInput.clear();
        coordXInput.clear();
        coordYInput.clear();
        areaInput.clear();
        populationInput.clear();
        metersInput.clear();
        timezoneInput.clear();
        populationDensityInput.clear();
        governmentInput.clear();
        governorNameInput.clear();
        governorAgeInput.clear();
        governorHeightInput.clear();
        governorBirthdayInput.clear();
    }

    @FXML
    private void idHintClick() {
        showHintDialog("id", LocalizeManager.getBundle().getString("hint.id"));
    }

    @FXML
    private void nameHintClick() {
        showHintDialog("name", LocalizeManager.getBundle().getString("hint.name"));
    }

    @FXML
    private void coordXHintClick() {
        showHintDialog("coordinate x", LocalizeManager.getBundle().getString("hint.x"));
    }

    @FXML
    private void coordYHintClick() {
        showHintDialog("coordinate y", LocalizeManager.getBundle().getString("hint.y"));
    }

    @FXML
    private void areaHintClick() {
        showHintDialog("area", LocalizeManager.getBundle().getString("hint.area"));
    }

    @FXML
    private void populationHintClick() {
        showHintDialog("population", LocalizeManager.getBundle().getString("hint.population"));
    }

    @FXML
    private void metersHintClick() {
        showHintDialog("metersAboveSeaLevel", LocalizeManager.getBundle().getString("hint.meters"));
    }

    @FXML
    private void timezoneHintClick() {
        showHintDialog("timezone", LocalizeManager.getBundle().getString("hint.timezone"));
    }

    @FXML
    private void populationDensityHintClick() {
        showHintDialog("populationDensity", LocalizeManager.getBundle().getString("hint.populationDensity"));
    }

    @FXML
    private void governmentHintClick() {
        showHintDialog("government", LocalizeManager.getBundle().getString("hint.government"));
    }

    @FXML
    private void governorNameHintClick() {
        showHintDialog("governorName", LocalizeManager.getBundle().getString("hint.governor.name"));
    }

    @FXML
    private void governorAgeHint() {
        showHintDialog("governorAge", LocalizeManager.getBundle().getString("hint.governor.age"));
    }

    @FXML
    private void governorHeightHint() {
        showHintDialog("governorHeight", LocalizeManager.getBundle().getString("hint.governor.height"));
    }

    @FXML
    private void governorBirthdayHintClick() {
        showHintDialog("governorBirthday", LocalizeManager.getBundle().getString("hint.governor.birthday"));
    }


    public static void showHintDialog(String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(LocalizeManager.getBundle().getString("hint"));
        alert.setHeaderText(LocalizeManager.getBundle().getString("hint") + header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showErrorDialog(String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(LocalizeManager.getBundle().getString("error"));
        alert.setHeaderText(LocalizeManager.getBundle().getString("error"));
        alert.setContentText(LocalizeManager.getBundle().getString("error.server"));
        System.out.println(content);
        alert.showAndWait();
    }

    private <T> T validateAndConvert(String input, Class<T> type, boolean canBeNull, Predicate<T> validator, String errorMessage) throws StringConvertException {
        T value = StringConverter.convertString(input, type, canBeNull);
        if (value == null) return null;
        if (!validator.test(value)) {
            throw new StringConvertException(errorMessage);
        }
        return value;
    }
}
