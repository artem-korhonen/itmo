package commands;

import data.City;
import main.ServerManager;
import main.Console;
import transport.Response;

// remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
public class RemoveGreaterCommand extends Command {
    private ServerManager serverManager;
    private Console console;
    
    public RemoveGreaterCommand(String name, String description, ServerManager serverManager, Console console) {
        super(name, description);
        this.serverManager = serverManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        City city = this.console.cityInput(1);
        Response response = this.serverManager.sendCommand(this.getName(), null, city);

        if (response != null) {
            response.printMessages();
        }
    }
}
