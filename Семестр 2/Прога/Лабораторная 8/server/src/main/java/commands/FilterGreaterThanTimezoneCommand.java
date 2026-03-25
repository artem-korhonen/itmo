package commands;

import java.util.List;
import java.util.ArrayList;

import data.City;
import main.CollectionManager;
import transport.Request;
import transport.Response;

// filter_greater_than_timezone timezone : вывести элементы, значение поля timezone которых больше заданного
public class FilterGreaterThanTimezoneCommand extends Command {
    private CollectionManager collectionManager;

    public FilterGreaterThanTimezoneCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        Long timezone = (Long) request.getNumber();
        List<String> cities = new ArrayList<>();

        for (City city : collectionManager.getCollection().values()) {
            if (city.getTimezone() > timezone) {
                cities.add(city.toString());
            }
        }
        System.out.println(cities);
        return new Response(cities, true, collectionManager.getCollection());
    }
}
