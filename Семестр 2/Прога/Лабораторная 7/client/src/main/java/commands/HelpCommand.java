package commands;

import main.CommandManager;

// help : вывести справку по доступным командам
public class HelpCommand extends Command {
    CommandManager commandManager;

    public HelpCommand(String name, String description, CommandManager commandManager) {
        super(name, description);
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) {
        for (Command command : commandManager.getCommands().values()) {
            System.out.println(command.getName() + " : " + command.getDescription());
        }
    }
}
