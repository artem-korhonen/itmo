package main;

import java.util.List;
import java.util.Hashtable;

import data.City;
import commands.ClearCommand;
import commands.Command;
import commands.FilterGreaterThanTimezoneCommand;
import commands.InfoCommand;
import commands.InsertCommand;
import commands.PrintFieldDescendingGovernorCommand;
import commands.PrintUniqueGovernorCommand;
import commands.RemoveGreaterCommand;
import commands.RemoveKeyCommand;
import commands.RemoveLowerKeyCommand;
import commands.ReplaceIfGreaterCommand;
import commands.ShowCommand;
import commands.UpdateCommand;

public class CommandManager {
      private Hashtable<String, Command> commands = new Hashtable<>();
      private CollectionManager collectionManager;

      public CommandManager(CollectionManager collectionManager) {
            this.collectionManager = collectionManager;
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
      }

      public List<String> executeCommand(String commandName, Number number, City city) {
            Command command = (Command) this.commands.get(commandName);

            if (command != null) {
                  return command.execute(number, city);
            } else {
                  Integer cityId = (Integer) number;
                  return collectionManager.containsTest(cityId) ? List.of("True") : List.of("False");
            }
      }

      public void executeServerCommand(ServerConsole serverConsole, String commandName) {
            if ("save".equals(commandName)) {
                  this.collectionManager.saveCollection();
                  System.out.println("Collection was saved");
            } else if ("exit".equals(commandName)) {
                  this.collectionManager.saveCollection();
                  System.out.println("Collection was saved");
                  serverConsole.breakConsole();
            }
      }
}
