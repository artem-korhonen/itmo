package commands;

import data.City;
import exceptions.StringConvertException;
import main.ServerManager;
import main.Console;
import main.StringConverter;
import transport.Response;

// replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого
public class ReplaceIfGreaterCommand extends Command {
    private ServerManager serverManager;
    private Console console;
    
    public ReplaceIfGreaterCommand(String name, String description, ServerManager serverManager, Console console) {
        super(name, description);
        this.serverManager = serverManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            try {
                Integer cityId = StringConverter.convertString(args[0], Integer.class, false);
                Response containsResponse = this.serverManager.sendCommand("containsId", cityId, null);

                if (containsResponse != null) {
                    if (containsResponse.getMessages().contains("True")) {
                        City city = this.console.cityInput(cityId);
                        Response response = this.serverManager.sendCommand(this.getName(), cityId, city);

                        if (response != null) {
                            response.printMessages();
                        }
                    } else {
                        System.out.println("Error: City with id " + cityId + " doesn't exist");
                    }
                }
            } catch (StringConvertException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Error: Input must have an argument");
        }

        
    }
}
