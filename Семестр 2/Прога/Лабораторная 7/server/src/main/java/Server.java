import main.DatabaseManager;
import main.ServerConsole;
import main.CommandManager;
import main.CollectionManager;

import java.sql.SQLException;

import main.ClientManager;


public class Server {
    public static void main(String[] args) {
        // CsvConverter csvConverter = new CsvConverter("resources/cities.csv");
        try {
            DatabaseManager databaseManager = new DatabaseManager("jdbc:postgresql://localhost:5432/itmo_proga", "postgres", "492238");
            CollectionManager collectionManager = new CollectionManager(databaseManager);
            CommandManager commandManager = new CommandManager(collectionManager, databaseManager);
            ClientManager clientManager = new ClientManager(1984, commandManager);

            ServerConsole serverConsole = new ServerConsole(commandManager);
            Thread consoleThread = new Thread(serverConsole);
            consoleThread.setDaemon(true);
            consoleThread.start();

            clientManager.start();
        } catch (SQLException e) {
            System.out.println("Error: Couldn't connect to the database");
        }
    }
}
