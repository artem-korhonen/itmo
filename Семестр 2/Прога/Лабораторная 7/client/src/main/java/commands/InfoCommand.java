package commands;

import main.ServerManager;
import transport.Response;

// info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
public class InfoCommand extends Command {
    private ServerManager serverManager;
    
    public InfoCommand(String name, String description, ServerManager serverManager) {
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
