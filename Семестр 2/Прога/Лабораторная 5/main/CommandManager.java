package main;

import commands.ClearCommand;
import commands.Command;
import commands.ExecuteScriptCommand;
import commands.ExitCommand;
import commands.FilterGreaterThanTimezoneCommand;
import commands.HelpCommand;
import commands.InfoCommand;
import commands.InsertCommand;
import commands.PrintFieldDescendingGovernorCommand;
import commands.PrintUniqueGovernorCommand;
import commands.RemoveGreaterCommand;
import commands.RemoveKeyCommand;
import commands.RemoveLowerKeyCommand;
import commands.ReplaceIfGreaterCommand;
import commands.SaveCommand;
import commands.ShowCommand;
import commands.UpdateCommand;

import java.util.Hashtable;

public class CommandManager {
      private Hashtable<String, Command> commands = new Hashtable<>();
      private CollectionManager collectionManager;
      private Console console;

      public CommandManager(CollectionManager collectionManager, Console console) {
            this.collectionManager = collectionManager;
            this.console = console;
            this.commands.put("help", new HelpCommand("help", "вывести справку по доступным командам"));
            this.commands.put("info",
                        new InfoCommand("info", "вывести информацию о коллекции", this.collectionManager));
            this.commands.put("show",
                        new ShowCommand("show",
                                    "вывести в стандартный поток вывода все элементы коллекции в строковом представлении",
                                    this.collectionManager));
            this.commands.put("insert",
                        new InsertCommand("insert", "добавить новый элемент с заданным ключом",
                                    this.collectionManager, this.console));
            this.commands.put("update", new UpdateCommand("update",
                        "обновить значение элемента коллекции, id которого равен заданному", this.collectionManager, this.console));
            this.commands.put("remove_key",
                        new RemoveKeyCommand("remove_key", "удалить элемент из коллекции по его ключу",
                                    this.collectionManager));
            this.commands.put("clear", new ClearCommand("clear", "очистить коллекцию", this.collectionManager));
            this.commands.put("save", new SaveCommand("save", "сохранить коллекцию в файл", this.collectionManager));
            this.commands.put("execute_script", new ExecuteScriptCommand("execute_script",
                        "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме",
                        this));
            this.commands.put("exit", new ExitCommand("exit", "завершить программу", console));
            this.commands.put("remove_greater", new RemoveGreaterCommand("remove_greater",
                        "удалить из коллекции все элементы, превышающие заданный", this.collectionManager, this.console));
            this.commands.put("replace_if_greater", new ReplaceIfGreaterCommand("replace_if_greater",
                        "заменить значение по ключу, если новое значение больше старого", this.collectionManager, this.console));
            this.commands.put("remove_lower_key", new RemoveLowerKeyCommand("remove_lower_key",
                        "удалить из коллекции все элементы, ключ которых меньше, чем заданный",
                        this.collectionManager));
            this.commands.put("filter_greater_than_timezone",
                        new FilterGreaterThanTimezoneCommand("filter_greater_than_timezone",
                                    "вывести элементы, значение поля timezone которых больше заданного",
                                    this.collectionManager));
            this.commands.put("print_unique_governor", new PrintUniqueGovernorCommand("print_unique_governor",
                        "вывести уникальные значения поля governor всех элементов в коллекции",
                        this.collectionManager));
            this.commands.put("print_field_descending_governor",
                        new PrintFieldDescendingGovernorCommand("print_field_descending_governor",
                                    "вывести значения поля governor всех элементов в порядке убывания",
                                    this.collectionManager));
      }

      public void executeCommand(String input) {
            String[] commandWords = input.split(" ", 2);
            Command command = (Command) this.commands.get(commandWords[0]);

            if (command != null) {
                  command.execute(commandWords.length == 2 ? commandWords[1].split(" ") : new String[] {});
            } else {
                  System.out.println("Command not found: " + commandWords[0]);
            }
      }
}
