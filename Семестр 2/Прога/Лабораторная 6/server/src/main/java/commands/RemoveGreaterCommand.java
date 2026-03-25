package commands;

import java.util.ArrayList;
import java.util.List;

import data.City;
import main.CollectionManager;

// remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
public class RemoveGreaterCommand extends Command {
    private CollectionManager collectionManager;
    
    public RemoveGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public List<String> execute(Number number, City city) {
        List<Integer> ids = new ArrayList<>();
        List<String> answer = new ArrayList<>();

        for (City cityFromCollection : collectionManager.getCollection().values()) {
            if (city.getArea() < cityFromCollection.getArea()) {
                ids.add(cityFromCollection.getId());
                answer.add(cityFromCollection.toString() + " has been removed");
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
