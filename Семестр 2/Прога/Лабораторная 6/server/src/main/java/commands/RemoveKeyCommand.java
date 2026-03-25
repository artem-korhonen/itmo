package commands;

import java.util.List;

import data.City;
import main.CollectionManager;

// remove_key null : удалить элемент из коллекции по его ключу
public class RemoveKeyCommand extends Command {
    private CollectionManager collectionManager;
    
    public RemoveKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public List<String> execute(Number number, City city) {
        Integer cityId = (Integer) number;
        if (collectionManager.getCollection().keySet().contains(cityId)) {
            List<String> answer = List.of(collectionManager.getCollection().get(cityId).toString() + " has been removed");
            collectionManager.removeElementCollection(cityId);
            return answer;
        } else {
            return List.of("Error: City with id " + cityId + " doesn't exist");
        }
    }
}
