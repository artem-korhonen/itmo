package commands;

import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

import data.City;
import main.CollectionManager;

// show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
public class ShowCommand extends Command {
    private CollectionManager collectionManager;
    
    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public List<String> execute(Number number, City city1) {
        Hashtable<Integer, City> cities = collectionManager.getCollection();
        List<String> answer = new ArrayList<>();

        for (City city : cities.values()) {
            answer.add(city.toString());
        }

        return answer;
    }
}
