package commands;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import data.City;
import main.CollectionManager;

// print_field_descending_governor : вывести значения поля governor всех элементов в порядке убывания
public class PrintFieldDescendingGovernorCommand extends Command {
    private CollectionManager collectionManager;
    
    public PrintFieldDescendingGovernorCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public List<String> execute(Number number, City city1) {
        List<City> sortedCities = new ArrayList<>(collectionManager.getCollection().values());
        sortedCities.sort(Comparator.comparingInt(City::getArea).reversed());
        List<String> answer = new ArrayList<>();

        for (City city : sortedCities) {
            answer.add(city.toString());
        }

        return answer;
    }
}
