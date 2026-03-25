package commands;

import data.City;
import exceptions.StringConvertException;
import main.ServerManager;
import main.Console;
import main.StringConverter;
import transport.Response;

// update id {element} : обновить значение элемента коллекции, id которого равен заданному
public class UpdateCommand extends Command {
    private ServerManager serverManager;
    private Console console;

    public UpdateCommand(String name, String description, ServerManager serverManager, Console console) {
        super(name, description);
        this.serverManager = serverManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            try {
                Integer cityId = StringConverter.convertString(args[0], Integer.class, false);
                Response containsResponse = this.serverManager.sendCommand("containsId", cityId, null, null);

                if (containsResponse != null) {
                    if (containsResponse.getState()) {
                        City city = this.console.cityInput(cityId);
                        Response response = this.serverManager.sendCommand(this.getName(), cityId, city, null);

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
