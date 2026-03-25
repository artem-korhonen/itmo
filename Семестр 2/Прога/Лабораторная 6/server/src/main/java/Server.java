import main.CsvConverter;
import main.ServerConsole;
import main.CommandManager;
import main.CollectionManager;
import main.ClientManager;


public class Server {
    public static void main(String[] args) {
        CsvConverter csvConverter = new CsvConverter("resources/cities.csv");
        CollectionManager collectionManager = new CollectionManager(csvConverter);
        CommandManager commandManager = new CommandManager(collectionManager);
        ClientManager clientManager = new ClientManager(12345, commandManager);

        ServerConsole serverConsole = new ServerConsole(commandManager);
        Thread consoleThread = new Thread(serverConsole);
        consoleThread.setDaemon(true);
        consoleThread.start();

        clientManager.start();
    }
}
