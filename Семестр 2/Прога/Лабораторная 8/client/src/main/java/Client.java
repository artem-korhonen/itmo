import main.*;
import ui.MainUI;
import javafx.application.Application;

public class Client {
    public static void main(String[] args) {
        ServerManager serverManager = new ServerManager("localhost", 1984);
        CommandManager commandManager = new CommandManager(serverManager);

        MainUI.setCommandManager(commandManager);
        Application.launch(MainUI.class, args);
    }
}