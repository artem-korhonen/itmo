package commands;

import exceptions.StringConvertException;
import main.ServerManager;
import main.StringConverter;
import transport.Response;

// filter_greater_than_timezone timezone : вывести элементы, значение поля timezone которых больше заданного
public class FilterGreaterThanTimezoneCommand extends Command {
    private ServerManager serverManager;

    public FilterGreaterThanTimezoneCommand(String name, String description, ServerManager serverManager) {
        super(name, description);
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            try {
                Float timezone = StringConverter.convertString(args[0], Float.class, false);
                Response response = this.serverManager.sendCommand(this.getName(), timezone, null, null);

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
