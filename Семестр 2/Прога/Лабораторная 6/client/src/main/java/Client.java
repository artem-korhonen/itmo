import main.*;

public class Client {
    public static void main(String[] args) {
        Console console = new Console();
        ServerManager serverManager = new ServerManager("localhost", 12345);
        CommandManager commandManager = new CommandManager(serverManager, console);

        console.startConsole(commandManager);
    }
}
