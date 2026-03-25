package commands;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

import main.CollectionManager;
import transport.Request;
import transport.Response;

// print_unique_governor : вывести уникальные значения поля governor всех элементов в коллекции
public class PrintUniqueGovernorCommand extends Command {
    private CollectionManager collectionManager;
    
    public PrintUniqueGovernorCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        Set<String> governors = new HashSet<>(collectionManager.getGovernors());

        return new Response(new ArrayList<String>(governors));
    }
}
