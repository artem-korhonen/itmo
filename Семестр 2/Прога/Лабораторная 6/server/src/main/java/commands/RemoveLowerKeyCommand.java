package commands;

import java.util.ArrayList;
import java.util.List;

import data.City;
import main.CollectionManager;

// remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный
public class RemoveLowerKeyCommand extends Command {
    private CollectionManager collectionManager;
    
    public RemoveLowerKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public List<String> execute(Number number, City city1) {
        Integer minId = (Integer) number;
        List<Integer> ids = new ArrayList<>();
        List<String> answer = new ArrayList<>();

        for (City city : collectionManager.getCollection().values()) {
            if (city.getId() < minId) {
                ids.add(city.getId());
                answer.add(collectionManager.getCollection().get(city.getId()) + " has been removed");
            }
        }

        if (ids.size() == 0) {
            return List.of("No cities have been removed");
        } else {
            collectionManager.removeElementsCollection(ids);
            return answer;
        }
    }
}
