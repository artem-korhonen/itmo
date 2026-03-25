package commands;

import main.ServerManager;
import transport.Response;

// clear : очистить коллекцию
public class ClearCommand extends Command {
    private ServerManager serverManager;

    public ClearCommand(String name, String description, ServerManager serverManager) {
        super(name, description);
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args) {
        Response response = serverManager.sendCommand(this.getName(), null, null, null);

        if (response != null) {
            response.printMessages();
        }
    }
}
