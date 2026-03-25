package commands;

import java.util.Hashtable;

import data.City;
import exceptions.StringConvertException;
import main.CollectionManager;
import main.Console;
import main.StringConverter;

// replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого
public class ReplaceIfGreaterCommand extends Command {
    private CollectionManager collectionManager;
    private Console console;
    
    public ReplaceIfGreaterCommand(String name, String description, CollectionManager collectionManager, Console console) {
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
                    Hashtable<Integer, City> cities = collectionManager.getCollection();
                    City city = this.console.cityInput(cityId);

                    if (city.getArea() > cities.get(cityId).getArea()) {
                        System.out.println(cities.get(cityId).toString() + " has been replaced by " + city.toString());
                        collectionManager.updateCollection(cityId, city);
                    } else {
                        System.out.println(cities.get(cityId).toString() + " hasn't been replaced");
                    }
                } else {
                    System.out.println("Error: City with id " + cityId + " is not exist");
                }
            } catch (StringConvertException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Error: Input must have an argument");
        }
    }
}
