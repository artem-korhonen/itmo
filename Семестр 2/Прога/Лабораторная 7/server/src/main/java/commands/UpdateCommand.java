package commands;

import java.util.List;

import data.City;
import main.CollectionManager;
import transport.Request;
import transport.Response;

// update id {element} : обновить значение элемента коллекции, id которого равен заданному
public class UpdateCommand extends Command {
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        Integer cityId = (Integer) request.getNumber();
        City city = request.getCity();
        if (collectionManager.getCollection().keySet().contains(cityId)) {
            if (collectionManager.updateCollection(cityId, city)) {
                return new Response(List.of("City with id " + cityId + " updated successfully"));
            } else {
                return new Response(List.of("Error: City with id " + cityId + " hasn't updated (database)"));
            }
        } else {
            return new Response(List.of("Error: City with id " + cityId + " doesn't exist"));
        }
    }
}
