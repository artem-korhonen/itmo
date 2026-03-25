package commands;

import main.Console;

// exit : завершить программу (без сохранения в файл)
public class ExitCommand extends Command {
    Console console;

    public ExitCommand(String name, String description, Console console) {
        super(name, description);
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        this.console.breakConsole();
    }
}
