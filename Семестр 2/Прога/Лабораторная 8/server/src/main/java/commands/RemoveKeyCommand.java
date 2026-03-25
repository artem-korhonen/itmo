package commands;

import java.util.List;

import main.CollectionManager;
import transport.Request;
import transport.Response;

// remove_key null : удалить элемент из коллекции по его ключу
public class RemoveKeyCommand extends Command {
    private CollectionManager collectionManager;
    
    public RemoveKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        Integer cityId = (Integer) request.getNumber();
        if (cityId != null) {
            if (collectionManager.getCollection().keySet().contains(cityId)) {
                if (collectionManager.removeElementCollection(cityId)) {
                    return new Response(List.of("City with id" + cityId + " has been removed"), true, collectionManager.getCollection());
                } else {
                    return new Response(List.of("Error: " + collectionManager.getCollection().get(cityId).toString() + " hasn't been removed (database)"), false, collectionManager.getCollection());
                }
            } else {
                return new Response(List.of("Error: City with id " + cityId + " doesn't exist"), false, collectionManager.getCollection());
            }
        } else {
            return new Response(List.of("Error: Null in id"), false, null);
        }
    }
}
