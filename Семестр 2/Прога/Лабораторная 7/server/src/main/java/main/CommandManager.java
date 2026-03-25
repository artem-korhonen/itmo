package main;

import java.util.Hashtable;

import transport.Request;
import transport.Response;
import commands.ClearCommand;
import commands.Command;
import commands.FilterGreaterThanTimezoneCommand;
import commands.InfoCommand;
import commands.InsertCommand;
import commands.LoginCommand;
import commands.PrintFieldDescendingGovernorCommand;
import commands.PrintUniqueGovernorCommand;
import commands.RemoveGreaterCommand;
import commands.RemoveKeyCommand;
import commands.RemoveLowerKeyCommand;
import commands.ReplaceIfGreaterCommand;
import commands.ShowCommand;
import commands.UpdateCommand;
import commands.RegisterCommand;

public class CommandManager {
      private Hashtable<String, Command> commands = new Hashtable<>();
      private CollectionManager collectionManager;
      private DatabaseManager databaseManager;

      public CommandManager(CollectionManager collectionManager, DatabaseManager databaseManager) {
            this.collectionManager = collectionManager;
            this.databaseManager = databaseManager;
            this.commands.put("info", new InfoCommand(this.collectionManager));
            this.commands.put("show", new ShowCommand(this.collectionManager));
            this.commands.put("insert", new InsertCommand(this.collectionManager));
            this.commands.put("update", new UpdateCommand(this.collectionManager));
            this.commands.put("remove_key", new RemoveKeyCommand(this.collectionManager));
            this.commands.put("clear", new ClearCommand(this.collectionManager));
            this.commands.put("remove_greater", new RemoveGreaterCommand(this.collectionManager));
            this.commands.put("replace_if_greater", new ReplaceIfGreaterCommand(this.collectionManager));
            this.commands.put("remove_lower_key", new RemoveLowerKeyCommand(this.collectionManager));
            this.commands.put("filter_greater_than_timezone", new FilterGreaterThanTimezoneCommand(this.collectionManager));
            this.commands.put("print_unique_governor", new PrintUniqueGovernorCommand(this.collectionManager));
            this.commands.put("print_field_descending_governor", new PrintFieldDescendingGovernorCommand(this.collectionManager));
            this.commands.put("login", new LoginCommand(this.databaseManager));
            this.commands.put("register", new RegisterCommand(this.databaseManager));
      }

      public Response executeCommand(Request request) {
            Command command = (Command) this.commands.get(request.getCommandName());

            if (command != null) {
                  return command.execute(request);
            } else {
                  Integer cityId = (Integer) request.getNumber();
                  return new Response(collectionManager.containsTest(cityId));
            }
      }

      public void executeServerCommand(ServerConsole serverConsole, String commandName) {
            if ("exit".equals(commandName)) {
                  serverConsole.breakConsole();
            }
      }
}
