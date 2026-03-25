package commands;

import exceptions.StringConvertException;
import main.CollectionManager;
import main.StringConverter;

// remove_key null : удалить элемент из коллекции по его ключу
public class RemoveKeyCommand extends Command {
    private CollectionManager collectionManager;
    
    public RemoveKeyCommand(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            try {
                Integer cityId = StringConverter.convertString(args[0], Integer.class, false);

                if (collectionManager.getCollection().keySet().contains(cityId)) {
                    System.out.println(collectionManager.getCollection().get(cityId).toString() + " has been removed");
                    collectionManager.removeElementCollection(cityId);
                } else {
                    System.out.println("Error: City with id " + cityId + " doesn't exist");
                }
            } catch (StringConvertException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Error: Input must have an argument");
        }
    }
}
