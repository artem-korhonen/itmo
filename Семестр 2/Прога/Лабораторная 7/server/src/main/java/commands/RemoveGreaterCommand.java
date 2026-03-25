package commands;

import java.util.ArrayList;
import java.util.List;

import data.City;
import main.CollectionManager;
import transport.Request;
import transport.Response;

// remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
public class RemoveGreaterCommand extends Command {
    private CollectionManager collectionManager;
    
    public RemoveGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        City city = request.getCity();
        List<Integer> ids = new ArrayList<>();
        List<String> answer = new ArrayList<>();

        for (City cityFromCollection : collectionManager.getCollection().values()) {
            if (city.getArea() < cityFromCollection.getArea()) {
                ids.add(cityFromCollection.getId());
                answer.add(cityFromCollection.toString() + " has been removed");
            }
        }

        if (ids.size() == 0) {
            return new Response(List.of("No cities have been removed"));
        } else {
            if (collectionManager.removeElementsCollection(ids)) {
                return new Response(answer);
            } else {
                return new Response(List.of("Error: No cities have been removed (database)"));
            }
        }
    }
}
