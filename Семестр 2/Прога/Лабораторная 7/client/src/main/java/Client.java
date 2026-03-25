import javax.swing.SwingUtilities;

import main.*;
import ui.AuthScreen;

public class Client {
    public static void main(String[] args) {
        // Console console = new Console();
        ServerManager serverManager = new ServerManager("localhost", 1984);
        CommandManager commandManager = new CommandManager(serverManager, null);

        // console.startConsole(commandManager);

        SwingUtilities.invokeLater(() -> {
            AuthScreen authScreen = new AuthScreen(commandManager);
            authScreen.setVisible(true);
        });
    }
}
