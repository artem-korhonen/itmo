package commands;

import main.ServerManager;
import transport.Response;

// print_unique_governor : вывести уникальные значения поля governor всех элементов в коллекции
public class PrintUniqueGovernorCommand extends Command {
    private ServerManager serverManager;
    
    public PrintUniqueGovernorCommand(String name, String description, ServerManager serverManager) {
        super(name, description);
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args) {
        Response response = this.serverManager.sendCommand(this.getName(), null, null, null);

        if (response != null) {
            response.printMessages();
        }
    }
}
