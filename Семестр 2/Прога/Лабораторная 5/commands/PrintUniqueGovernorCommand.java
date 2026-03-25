package commands;

import java.util.HashSet;
import java.util.Set;

import main.CollectionManager;

// print_unique_governor : вывести уникальные значения поля governor всех элементов в коллекции
public class PrintUniqueGovernorCommand extends Command {
    private CollectionManager collectionManager;
    
    public PrintUniqueGovernorCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        Set<String> governors = new HashSet<>();

        for (String governor : collectionManager.getGovernors().values()) {
            governors.add(governor);
        }

        for (String governor : governors) {
            System.out.println(governor);
        }
    }
}
