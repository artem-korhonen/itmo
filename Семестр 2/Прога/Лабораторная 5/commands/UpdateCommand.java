package commands;

import data.City;
import exceptions.StringConvertException;
import main.CollectionManager;
import main.Console;
import main.StringConverter;

// update id {element} : обновить значение элемента коллекции, id которого равен заданному
public class UpdateCommand extends Command {
    private CollectionManager collectionManager;
    private Console console;

    public UpdateCommand(String name, String description, CollectionManager collectionManager, Console console) {
        super(name, description);
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            try {
                Integer cityId = StringConverter.convertString(args[0], Integer.class, false);
                
                if (collectionManager.getCollection().keySet().contains(cityId)) {
                    City city = this.console.cityInput(cityId);
                    collectionManager.updateCollection(cityId, city);
                } else {
                    System.out.println("Error: City with id " + cityId + " doesn't exist");
                }
            } catch (StringConvertException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Error: Input must have an argument");
        }
    }
}
