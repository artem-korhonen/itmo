package commands;

import java.util.Hashtable;
import java.util.List;

import data.City;
import main.CollectionManager;

// replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого
public class ReplaceIfGreaterCommand extends Command {
    private CollectionManager collectionManager;
    
    public ReplaceIfGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public List<String> execute(Number number, City city) {
        Integer cityId = (Integer) number;
        Hashtable<Integer, City> cities = collectionManager.getCollection();

        if (city.getArea() > cities.get(cityId).getArea()) {
            String answer = cities.get(cityId).toString() + " has been replaced by " + city.toString();
            collectionManager.updateCollection(cityId, city);
            return List.of(answer);
        } else {
            return List.of(cities.get(cityId).toString() + " hasn't been replaced");
        }
    }
}
