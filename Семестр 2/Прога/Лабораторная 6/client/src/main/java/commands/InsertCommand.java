package commands;

import java.util.List;

import data.City;
import exceptions.StringConvertException;
import main.Console;
import main.StringConverter;
import main.ServerManager;
import transport.Response;

// insert null {element} : добавить новый элемент с заданным ключом
public class InsertCommand extends Command {
    private ServerManager serverManager;
    private Console console;
    
    public InsertCommand(String name, String description, ServerManager serverManager, Console console) {
        super(name, description);
        this.serverManager = serverManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            try {
                Integer cityId = StringConverter.convertString(args[0], Integer.class, true);

                if (cityId > 0) {
                    Response containsResponse = this.serverManager.sendCommand("containsId", cityId, null);

                    if (containsResponse != null) {
                        List<String> responseMessages = containsResponse.getMessages();
                        System.out.println(responseMessages.get(0));
                        if (responseMessages.contains("False")) {
                            City city = this.console.cityInput(cityId);
                            Response response = this.serverManager.sendCommand(this.getName(), cityId, city);

                            if (response != null) {
                                response.printMessages();
                            }
                        } else {
                            System.out.println("Error: City with id " + cityId + " already exists");
                        }
                    }
                } else {
                    System.out.println("Error: Id must be > 0");
                }
            } catch (StringConvertException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Error: Input must have an argument");
        }
    }
}
