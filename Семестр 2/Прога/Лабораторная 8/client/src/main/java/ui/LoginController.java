package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.CommandManager;
import transport.Request;
import transport.Response;
import user.Profile;
import main.LocalizeManager;

public class LoginController {
    private CommandManager commandManager;
    private Stage stage;
    
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button LoginButton;
    @FXML private Button RegisterButton;

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void LoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Response response = this.commandManager.executeCommand(new Request("login", null, null, new Profile(username, password)));
        
        if (response != null && response.getState()) {
            openMainWindow(username);
        } else {
            String errorMessage = response != null ? response.getMessage() : LocalizeManager.getBundle().getString("error");
            showErrorDialog(errorMessage);
        }
    }

    @FXML
    private void RegisterButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Response response = this.commandManager.executeCommand(new Request("register", null, null, new Profile(username, password)));

        if (response != null && response.getState()) {
            
        } else {
            String errorMessage = response != null ? response.getMessage() : LocalizeManager.getBundle().getString("error");
            showErrorDialog(errorMessage);
        }
    }

    @FXML
    private void LanguageButtonClick() {
        showLanguagesDialogAndWait();
    }

    private void openMainWindow(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main.fxml"));
            loader.setResources(LocalizeManager.getBundle());
            Parent root = loader.load();
            
            Stage mainStage = new Stage();
            mainStage.setTitle(":D");
            mainStage.setScene(new Scene(root));

            MainController controller = loader.getController();
            controller.setCommandManager(commandManager);
            controller.setUsername(username);
            controller.setStage(mainStage);
            controller.startAutoUpdate();
            
            if (this.stage != null) {
                this.stage.close();
            }
            
            mainStage.show();
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showErrorDialog(String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showLanguagesDialogAndWait() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/languages.fxml"));
            Parent root = loader.load();

            LanguagesController controller = loader.getController();

            Stage dialogStage = new Stage();
            controller.setStage(dialogStage);
            dialogStage.setTitle("Enter something");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.stage);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            usernameLabel.setText(LocalizeManager.getBundle().getString("username"));
            passwordLabel.setText(LocalizeManager.getBundle().getString("password"));
            LoginButton.setText(LocalizeManager.getBundle().getString("login"));
            RegisterButton.setText(LocalizeManager.getBundle().getString("register"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}