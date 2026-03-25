package commands;

import main.CommandManager;
import main.Console;
import main.ServerManager;
import transport.Response;
import user.Profile;

public class RegisterCommand extends Command {
    private CommandManager commandManager;
    private ServerManager serverManager;
    // private Console console;

    public RegisterCommand(String name, String description, CommandManager commandManager, ServerManager serverManager, Console console) {
        super(name, description);
        this.commandManager = commandManager;
        this.serverManager = serverManager;
        // this.console = console;
    }

    @Override
    public void execute(String args[]) {
        Profile profile = new Profile(args[1], args[2]);
        Response response = this.serverManager.sendCommand(this.getName(), null, null, profile);
        
        if (response != null) {
            response.printMessages();
        
            if (response.getState()) {
                this.commandManager.login();
            }
        }
    }
}
