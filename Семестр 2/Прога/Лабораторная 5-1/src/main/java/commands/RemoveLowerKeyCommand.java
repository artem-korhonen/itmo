package commands;

import java.util.ArrayList;
import java.util.List;

import data.City;
import exceptions.StringConvertException;
import main.CollectionManager;
import main.StringConverter;

// remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный
public class RemoveLowerKeyCommand extends Command {
    private CollectionManager collectionManager;
    
    public RemoveLowerKeyCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            try {
                Integer minId = StringConverter.convertString(args[0], Integer.class, false);
                List<Integer> ids = new ArrayList<>();

                for (City city : collectionManager.getCollection().values()) {
                    if (city.getId() < minId) {
                        ids.add(city.getId());
                        System.out.println(collectionManager.getCollection().get(city.getId()) + " has been removed");
                    }
                }

                if (ids.size() == 0) {
                    System.out.println("No cities have been removed");
                } else {
                    collectionManager.removeElementsCollection(ids);
                }
            } catch (StringConvertException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Error: Input must have an argument");
        }
    }
}
