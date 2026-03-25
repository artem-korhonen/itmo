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

public class IdInputController {
    private Stage stage;
    public Integer id;

    @FXML
    private TextField idInput;

    @FXML
    private Button ClearButton;
    @FXML
    private Button InputButton;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Integer getId() {
        return this.id;
    }

    @FXML
    private void inputButtonClick() {
        try {
            this.id = validateAndConvert(idInput.getText(), Integer.class, false, i -> i > 0, "Id must be > 0");
            this.stage.close();
        } catch (StringConvertException e) {
            showErrorDialog(e.getMessage());
        }
    }

    @FXML
    private void clearButtonClick() {
        this.idInput.clear();
    }

    @FXML
    private void idHintClick() {
        showHintDialog("id", LocalizeManager.getBundle().getString("hint.id"));
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
