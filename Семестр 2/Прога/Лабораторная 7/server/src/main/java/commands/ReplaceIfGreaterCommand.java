package commands;

import java.util.Hashtable;
import java.util.List;

import data.City;
import main.CollectionManager;
import transport.Request;
import transport.Response;

// replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого
public class ReplaceIfGreaterCommand extends Command {
    private CollectionManager collectionManager;
    
    public ReplaceIfGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        Integer cityId = (Integer) request.getNumber();
        City city = request.getCity();
        Hashtable<Integer, City> cities = collectionManager.getCollection();

        if (city.getArea() > cities.get(cityId).getArea()) {
            if (collectionManager.updateCollection(cityId, city)) {
                return new Response(List.of(cities.get(cityId).toString() + " has been replaced by " + city.toString()));
            } else {
                return new Response(List.of("Error: " + cities.get(cityId).toString() + " hasn't been replaced (database)"));
            }
        } else {
            return new Response(List.of(cities.get(cityId).toString() + " hasn't been replaced"));
        }
    }
}
