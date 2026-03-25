package commands;

import java.util.List;

import data.City;
import main.IdGenerator;
import transport.Request;
import transport.Response;
import main.CollectionManager;

// insert null {element} : добавить новый элемент с заданным ключом
public class InsertCommand extends Command {
    private CollectionManager collectionManager;
    
    public InsertCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        Integer cityId = (Integer) request.getNumber();
        City city = request.getCity();
        if (cityId == null) {
            cityId = IdGenerator.generateId();
            city.changeId(cityId);
        } else {
            IdGenerator.addException(cityId);
        }

        if (this.collectionManager.addToCollection(city)) {
            return new Response(List.of("City with id " + cityId + " has been added"));
        } else {
            return new Response(List.of("Error: City with id " + cityId + " hasn't been added (database)"));
        }
    }
}
