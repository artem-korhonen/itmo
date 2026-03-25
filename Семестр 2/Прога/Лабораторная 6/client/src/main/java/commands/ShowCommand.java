package commands;

import main.ServerManager;
import transport.Response;

// show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
public class ShowCommand extends Command {
    private ServerManager serverManager;
    
    public ShowCommand(String name, String description, ServerManager serverManager) {
        super(name, description);
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args) {
        Response response = this.serverManager.sendCommand(this.getName(), null, null);

        if (response != null) {
            response.printMessages();
        }
    }
}
