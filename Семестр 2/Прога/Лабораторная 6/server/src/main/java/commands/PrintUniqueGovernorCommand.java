package commands;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import data.City;
import main.CollectionManager;

// print_unique_governor : вывести уникальные значения поля governor всех элементов в коллекции
public class PrintUniqueGovernorCommand extends Command {
    private CollectionManager collectionManager;
    
    public PrintUniqueGovernorCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public List<String> execute(Number number, City city) {
        Set<String> governors = new HashSet<>(collectionManager.getGovernors());

        return new ArrayList<String>(governors);
    }
}
