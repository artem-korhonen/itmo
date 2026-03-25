package commands;

import java.util.List;

import main.CollectionManager;
import transport.Request;
import transport.Response;

// info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
public class InfoCommand extends Command {
    private CollectionManager collectionManager;
    
    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(List.of(
            "Collection name - cities",
            "Collection class - " + this.collectionManager.getCollectionClass(),
            "Collection size - " + this.collectionManager.getCollectionSize(),
            "Collection initialize date - " + this.collectionManager.getCollectionInitializeDate()
        ), true, collectionManager.getCollection());
    }
}
