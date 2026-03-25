package commands;

import exceptions.StringConvertException;
import main.ServerManager;
import main.StringConverter;
import transport.Response;

// remove_key null : удалить элемент из коллекции по его ключу
public class RemoveKeyCommand extends Command {
    private ServerManager serverManager;
    
    public RemoveKeyCommand(String name, String description, ServerManager serverManager) {
        super(name, description);
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            try {
                Integer cityId = StringConverter.convertString(args[0], Integer.class, false);
                Response response = this.serverManager.sendCommand(this.getName(), cityId, null, null);

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
