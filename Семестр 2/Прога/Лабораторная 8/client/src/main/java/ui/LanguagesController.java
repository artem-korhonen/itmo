package ui;

import java.util.Locale;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import main.LocalizeManager;

public class LanguagesController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void russianClick() {
        LocalizeManager.setLocale(new Locale("ru"));
    }

    @FXML
    private void englishClick() {
        LocalizeManager.setLocale(new Locale("en"));
    }

    @FXML
    private void spanishClick() {
        LocalizeManager.setLocale(new Locale("es"));
    }

    @FXML
    private void dutchClick() {
        LocalizeManager.setLocale(new Locale("nl"));
    }

    @FXML
    private void swedishClick() {
        LocalizeManager.setLocale(new Locale("sv"));
    }
}
