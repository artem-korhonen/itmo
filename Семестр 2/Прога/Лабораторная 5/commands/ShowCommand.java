package commands;

import java.util.Hashtable;

import data.City;
import main.CollectionManager;

// show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
public class ShowCommand extends Command {
    private CollectionManager collectionManager;
    
    public ShowCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        Hashtable<Integer, City> cities = collectionManager.getCollection();

        for (City city : cities.values()) {
            System.out.println(city.toString());
        }
    }
}
