package commands;

import java.util.List;

import main.CollectionManager;
import transport.Request;
import transport.Response;

// clear : очистить коллекцию
public class ClearCommand extends Command {
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        if (collectionManager.clearCollection()) {
            return new Response(List.of("Collection has been cleared"), true, collectionManager.getCollection());
        } else {
            return new Response(List.of("Error: Collection hasn't been cleared (database)"), false, collectionManager.getCollection());
        }
    }
}
