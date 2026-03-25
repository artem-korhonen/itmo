package commands;

import java.util.ArrayList;
import java.util.List;

import data.City;
import main.CollectionManager;
import main.Console;
import main.IdGenerator;

// remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
public class RemoveGreaterCommand extends Command {
    private CollectionManager collectionManager;
    private Console console;
    
    public RemoveGreaterCommand(String name, String description, CollectionManager collectionManager, Console console) {
        super(name, description);
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        List<Integer> ids = new ArrayList<>();
        City city = this.console.cityInput(IdGenerator.generateId());

        for (City cityFromCollection : collectionManager.getCollection().values()) {
            if (city.getArea() < cityFromCollection.getArea()) {
                ids.add(cityFromCollection.getId());
                System.out.println(cityFromCollection.toString() + " has been removed");
            }
        }

        if (ids.size() == 0) {
            System.out.println("No cities have been removed");
        } else {
            collectionManager.removeElementsCollection(ids);
        }
    }
}
