package commands;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import data.City;
import main.CollectionManager;

// print_field_descending_governor : вывести значения поля governor всех элементов в порядке убывания
public class PrintFieldDescendingGovernorCommand extends Command {
    private CollectionManager collectionManager;
    
    public PrintFieldDescendingGovernorCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        List<City> sortedCities = new ArrayList<>(collectionManager.getCollection().values());
        sortedCities.sort(Comparator.comparingInt(City::getArea).reversed());

        for (City city : sortedCities) {
            System.out.println(city.getGovernor().toString());
        }
    }
}
