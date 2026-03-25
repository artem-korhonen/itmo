package commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import main.CommandManager;

// execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме
public class ExecuteScriptCommand extends Command {
    CommandManager commandManager;
    public ExecuteScriptCommand(String name, String description, CommandManager commandManager) {
        super(name, description);
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            String filePath = args[0];
            
            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                String line;

                while ((line = br.readLine()) != null) {
                    if (!line.startsWith("execute_script")) {
                        commandManager.executeCommand(line);
                    } else {
                        System.out.println("Error: Command execute_script can't be used in script");
                    }
                }

                br.close();
            } catch (IOException e) {
                System.out.println("Error: File not found");
            }

        } else {
            System.out.println("Error: Input must have an argument");
        }
    }
}