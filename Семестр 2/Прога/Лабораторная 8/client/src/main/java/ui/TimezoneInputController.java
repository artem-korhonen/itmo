package ui;

import java.util.function.Predicate;

import exceptions.StringConvertException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.StringConverter;
import main.LocalizeManager;

public class TimezoneInputController {
    private Stage stage;
    public Long timezone;

    @FXML
    private TextField timezoneInput;

    @FXML
    private Button ClearButton;
    @FXML
    private Button InputButton;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Long getTimezone() {
        return this.timezone;
    }

    @FXML
    private void inputButtonClick() {
        try {
            this.timezone = validateAndConvert(timezoneInput.getText(), Long.class, false, i -> i > 0, "Timezone must be > -13 and <= 15");
            this.stage.close();
        } catch (StringConvertException e) {
            showErrorDialog(e.getMessage());
        }
    }

    @FXML
    private void clearButtonClick() {
        this.timezoneInput.clear();
    }

    @FXML
    private void timezoneHintClick() {
        showHintDialog("timezone", LocalizeManager.getBundle().getString("hint.timezone"));
    }

    public static void showHintDialog(String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(LocalizeManager.getBundle().getString("hint"));
        alert.setHeaderText(LocalizeManager.getBundle().getString("hint") + " " + header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showErrorDialog(String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(LocalizeManager.getBundle().getString("error"));
        alert.setHeaderText(LocalizeManager.getBundle().getString("error"));
        alert.setContentText(content);
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
