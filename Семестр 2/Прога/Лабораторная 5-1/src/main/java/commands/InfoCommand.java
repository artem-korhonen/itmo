package commands;

import main.CollectionManager;

// info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
public class InfoCommand extends Command {
    private CollectionManager collectionManager;
    
    public InfoCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Collection name - cities");
        System.out.println("Collection class - " + collectionManager.getCollectionClass());
        System.out.println("Collection size - " + collectionManager.getCollectionSize());
        System.out.println("Collection initialize date - " + collectionManager.getCollectionInitializeDate());
    }
}
