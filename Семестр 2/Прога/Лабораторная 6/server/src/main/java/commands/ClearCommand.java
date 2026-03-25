package commands;

import java.util.List;

import main.CollectionManager;
import data.City;

// clear : очистить коллекцию
public class ClearCommand extends Command {
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public List<String> execute(Number number, City city) {
        collectionManager.clearCollection();
        return List.of("Collection has been cleared");
    }
}
