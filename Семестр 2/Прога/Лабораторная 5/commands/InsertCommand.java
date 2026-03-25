package commands;

import main.CollectionManager;

import data.City;
import exceptions.StringConvertException;
import main.Console;
import main.IdGenerator;
import main.StringConverter;

// insert null {element} : добавить новый элемент с заданным ключом
public class InsertCommand extends Command {
    private CollectionManager collectionManager;
    private Console console;
    
    public InsertCommand(String name, String description, CollectionManager collectionManager, Console console) {
        super(name, description);
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            try {
                Integer cityId = StringConverter.convertString(args[0], Integer.class, true);

                if (cityId == null) {
                    cityId = IdGenerator.generateId();
                }
                
                if (!collectionManager.getCollection().keySet().contains(cityId)) {
                    City city = this.console.cityInput(cityId);
                    this.collectionManager.addToCollection(city);
                    System.out.println("City with id " + cityId + " has been added");
                } else {
                    System.out.println("Error: City with id " + cityId + " already exists");
                }
            } catch (StringConvertException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Error: Input must have an argument");
        }
    }
}
