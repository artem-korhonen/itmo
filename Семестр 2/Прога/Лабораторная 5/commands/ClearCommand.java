package commands;

import main.CollectionManager;

// clear : очистить коллекцию
public class ClearCommand extends Command {
    private CollectionManager collectionManager;

    public ClearCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        collectionManager.clearCollection();
        System.out.println("Collection has been cleared");
    }
}
