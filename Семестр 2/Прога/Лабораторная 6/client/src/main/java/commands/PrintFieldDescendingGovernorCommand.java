package commands;

import main.ServerManager;
import transport.Response;

// print_field_descending_governor : вывести значения поля governor всех элементов в порядке убывания
public class PrintFieldDescendingGovernorCommand extends Command {
    private ServerManager serverManager;
    
    public PrintFieldDescendingGovernorCommand(String name, String description, ServerManager serverManager) {
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
