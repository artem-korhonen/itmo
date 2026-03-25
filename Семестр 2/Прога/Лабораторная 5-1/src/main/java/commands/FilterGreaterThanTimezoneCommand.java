package commands;

import java.util.Hashtable;

import data.City;
import exceptions.StringConvertException;
import main.CollectionManager;
import main.StringConverter;

// filter_greater_than_timezone timezone : вывести элементы, значение поля timezone которых больше заданного
public class FilterGreaterThanTimezoneCommand extends Command {
    private CollectionManager collectionManager;

    public FilterGreaterThanTimezoneCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        Hashtable<Integer, City> cities = collectionManager.getCollection();

        if (args.length >= 1) {
            try {
                Float timezone = StringConverter.convertString(args[0], Float.class, false);

                for (City city : cities.values()) {
                    if (city.getTimezone() > timezone) {
                        System.out.println(city.toString());
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
