package commands;

import java.util.List;

import data.City;
import main.IdGenerator;
import main.CollectionManager;

// insert null {element} : добавить новый элемент с заданным ключом
public class InsertCommand extends Command {
    private CollectionManager collectionManager;
    
    public InsertCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public List<String> execute(Number number, City city) {
        Integer cityId = (Integer) number;
        if (cityId == null) {
            cityId = IdGenerator.generateId();
            city.changeId(cityId);
        } else {
            IdGenerator.addException(cityId);
        }

        this.collectionManager.addToCollection(city);
        return List.of("City with id " + cityId + " has been added");
    }
}
