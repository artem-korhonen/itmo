package commands;

import exceptions.StringConvertException;
import main.ServerManager;
import main.StringConverter;
import transport.Response;

// remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный
public class RemoveLowerKeyCommand extends Command {
    private ServerManager serverManager;
    
    public RemoveLowerKeyCommand(String name, String description, ServerManager serverManager) {
        super(name, description);
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            try {
                Integer minId = StringConverter.convertString(args[0], Integer.class, false);
                Response response = this.serverManager.sendCommand(this.getName(), minId, null, null);

                if (response != null) {
                    response.printMessages();
                }
            } catch (StringConvertException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Error: Input must have an argument");
        }
    }
}
