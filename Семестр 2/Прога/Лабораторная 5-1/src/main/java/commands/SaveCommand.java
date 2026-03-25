package commands;

import main.CollectionManager;

// save : сохранить коллекцию в файл
public class SaveCommand extends Command {
    private CollectionManager collectionManager;
    
    public SaveCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        this.collectionManager.saveCollection();
        System.out.println("Collection has been saved");
    }
}
