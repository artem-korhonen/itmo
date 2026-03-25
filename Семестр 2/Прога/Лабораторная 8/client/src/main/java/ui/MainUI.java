package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import main.CommandManager;
import main.LocalizeManager;

public class MainUI extends Application {
    private static CommandManager commandManager;
    
    public static void setCommandManager(CommandManager commandManager) {
        MainUI.commandManager = commandManager;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/auth2.fxml"));
        loader.setResources(LocalizeManager.getBundle());
        Parent root = loader.load();
        
        LoginController controller = loader.getController();
        controller.setCommandManager(commandManager);
        controller.setStage(stage);
        
        stage.setTitle(LocalizeManager.getBundle().getString("login") + "/" + LocalizeManager.getBundle().getString("register"));
        stage.setScene(new Scene(root, 300, 200));
        stage.show();
    }

    public static void main(String[] args) {
    }
}