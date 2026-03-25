package ui;

import java.io.IOException;
import java.util.Hashtable;

import data.City;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.CommandManager;
import transport.Request;
import transport.Response;
import main.LocalizeManager;

public class MainController {
    private CommandManager commandManager;
    private Stage stage;
    private String username;
    private TableManager tableManager;
    private VisualizationManager visualizationManager;
    private Hashtable<Integer, City> cities;

    @FXML private Label usernameLabel;
    @FXML private TextArea console;
    @FXML private Canvas canvas;
    @FXML private TextArea commandsInfo;
    @FXML private Button logoutButton;

    @FXML
    private TableView<City> citiesTable;

    @FXML private TableColumn<City, Integer> idColumn;
    @FXML private TableColumn<City, String> usernameColumn;
    @FXML private TableColumn<City, String> nameColumn;
    @FXML private TableColumn<City, Double> coordXColumn;
    @FXML private TableColumn<City, Double> coordYColumn;
    @FXML private TableColumn<City, Integer> areaColumn;
    @FXML private TableColumn<City, Integer> populationColumn;
    @FXML private TableColumn<City, Double> meterAboveSeaLevelColumn;
    @FXML private TableColumn<City, Long> populationDensityColumn;
    @FXML private TableColumn<City, String> governmentColumn;
    @FXML private TableColumn<City, String> governorNameColumn;
    @FXML private TableColumn<City, Integer> governorAgeColumn;
    @FXML private TableColumn<City, Double> governorHeightColumn;
    @FXML private TableColumn<City, String> governorBirthdayColumn;

    public void initialize() {
        this.tableManager = new TableManager(
            citiesTable,
            idColumn,
            usernameColumn,
            nameColumn,
            coordXColumn,
            coordYColumn,
            areaColumn,
            populationColumn,
            meterAboveSeaLevelColumn,
            populationDensityColumn,
            governmentColumn,
            governorNameColumn,
            governorAgeColumn,
            governorHeightColumn,
            governorBirthdayColumn
        );

        this.visualizationManager = new VisualizationManager();
    }

    public void startAutoUpdate() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Response response = commandManager.executeCommand(new Request("show", null, null, null));
                    Hashtable<Integer, City> newCollection = response.getCities();
                    tableManager.updateTable(newCollection);
                    this.visualizationManager.drawCities(newCollection, this.canvas);
                    this.cities = newCollection;
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private City showCityInputDialogAndWait() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/input.fxml"));
            loader.setResources(LocalizeManager.getBundle());
            Parent root = loader.load();

            CityInputController controller = loader.getController();

            Stage dialogStage = new Stage();
            controller.setStage(dialogStage);
            controller.setUsername(username);
            dialogStage.setTitle("Enter something");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.stage);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            return controller.getCity();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Integer showIdInputDialogAndWait() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/keyinput.fxml"));
            loader.setResources(LocalizeManager.getBundle());
            Parent root = loader.load();

            IdInputController controller = loader.getController();

            Stage dialogStage = new Stage();
            controller.setStage(dialogStage);
            dialogStage.setTitle("");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.stage);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            return controller.getId();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Long showTimezoneInputDialogAndWait() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/timezoneinput.fxml"));
            loader.setResources(LocalizeManager.getBundle());
            Parent root = loader.load();

            TimezoneInputController controller = loader.getController();

            Stage dialogStage = new Stage();
            controller.setStage(dialogStage);
            dialogStage.setTitle("Enter something");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.stage);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            return controller.getTimezone();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showLanguagesDialogAndWait() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/languages.fxml"));
            Parent root = loader.load();

            LanguagesController controller = loader.getController();

            Stage dialogStage = new Stage();
            controller.setStage(dialogStage);
            dialogStage.setTitle("");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.stage);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            this.logoutButton.setText(LocalizeManager.getBundle().getString("logout"));
            this.commandsInfo.setText(LocalizeManager.getBundle().getString("commands.info"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUsername(String username) {
        this.username = username;
        this.usernameLabel.setText(username);
    }

    @FXML
    public void insertButtonClick() {
        City city = showCityInputDialogAndWait();
        Response response = commandManager.executeCommand(new Request("insert", city.getId(), city, null));
        responseWork(response);
    }

    @FXML
    private void updateButtonClick() {
        City city = showCityInputDialogAndWait();
        Response response = commandManager.executeCommand(new Request("update", city.getId(), city, null));
        responseWork(response);
    }

    @FXML
    private void removeKeyButtonClick() {
        Integer id = showIdInputDialogAndWait();
        Response response = commandManager.executeCommand(new Request("remove_key", id, null, null));
        responseWork(response);
    }

    @FXML
    private void clearButtonClick() {
        Response response = this.commandManager.executeCommand(new Request("clear", null, null, null));
        responseWork(response);
    }

    @FXML
    private void removeGreaterButtonClick() {
        City city = showCityInputDialogAndWait();
        Response response = commandManager.executeCommand(new Request("remove_greater", null, city, null));
        responseWork(response);
    }

    @FXML
    private void replaceIfGreaterButtonClick() {
        City city = showCityInputDialogAndWait();
        Response response = commandManager.executeCommand(new Request("replace_if_greater", city.getId(), city, null));
        responseWork(response);
    }

    @FXML
    private void removeLowerKeyButtonClick() {
        Integer id = showIdInputDialogAndWait();
        Response response = commandManager.executeCommand(new Request("remove_lower_key", id, null, null));
        responseWork(response);
    }

    @FXML
    private void filterTimezoneButtonClick() {
        Long timezone = showTimezoneInputDialogAndWait();
        Response response = commandManager.executeCommand(new Request("filter_greater_than_timezone", timezone, null, null));
        responseWork(response);
    }

    @FXML
    private void printUniqueGovernorButtonClick() {
        Response response = this.commandManager.executeCommand(new Request("print_unique_governor", null, null, null));
        responseWork(response);
    }

    @FXML
    private void printGovernorButtonClick() {
        Response response = this.commandManager.executeCommand(new Request("print_field_descending_governor", null, null, null));
        responseWork(response);
    }

    @FXML
    private void infoButtonClick() {
        Response response = this.commandManager.executeCommand(new Request("info", null, null, null));
        responseWork(response);
    }

    @FXML
    private void exitButtonClick() {
        if (this.stage != null) {
            this.stage.close();
        }
    }

    @FXML
    private void logoutButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/auth2.fxml"));
            loader.setResources(LocalizeManager.getBundle());
            
            Parent root = loader.load();
            
            Stage loginStage = new Stage();
            loginStage.setTitle(LocalizeManager.getBundle().getString("login") + "/" + LocalizeManager.getBundle().getString("register"));
            loginStage.setScene(new Scene(root));

            LoginController controller = loader.getController();
            controller.setCommandManager(commandManager);
            controller.setStage(loginStage);
            
            if (this.stage != null) {
                this.stage.close();
            }
            
            loginStage.show();
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
        }
    }

    @FXML
    private void languagesButtonClick() {
        showLanguagesDialogAndWait();
    }

    private void responseWork(Response response) {
        if (response != null) {
            if (response.getCities() != null) {
                this.tableManager.updateTable(response.getCities());
            }

            if (!response.getState()) {
                showErrorDialog(response.getMessage());
            }

            this.console.setText(response.getMessage());
        } else {
            showErrorDialog(LocalizeManager.getBundle().getString("error"));
        }
    }

    @FXML
    private void canvasClick(MouseEvent event) {
        double clickX = event.getX();
        double clickY = event.getY();
        City city = this.visualizationManager.canvasClick(this.cities, clickX, clickY);
        
        if (city != null) {
            showInfoDialog(city.toString());
        }
    }

    public static void showErrorDialog(String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(content);
        alert.showAndWait();
    }  

    public static void showInfoDialog(String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(LocalizeManager.getBundle().getString("info"));
        alert.setHeaderText(LocalizeManager.getBundle().getString("info"));
        alert.setContentText(content);
        alert.showAndWait();
    }
}
