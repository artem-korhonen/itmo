package commands;

import java.util.List;

import data.City;
import main.CollectionManager;

// update id {element} : обновить значение элемента коллекции, id которого равен заданному
public class UpdateCommand extends Command {
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public List<String> execute(Number number, City city) {
        Integer cityId = (Integer) number;
        if (collectionManager.getCollection().keySet().contains(cityId)) {
            collectionManager.updateCollection(cityId, city);
            return List.of("City with id " + cityId + " updated successfully");
        } else {
            return List.of("Error: City with id " + cityId + " doesn't exist");
        }
    }
}
