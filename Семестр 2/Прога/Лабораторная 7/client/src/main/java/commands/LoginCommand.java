package commands;

import main.CommandManager;
import main.Console;
import main.ServerManager;
import transport.Response;
import user.Profile;

public class LoginCommand extends Command {
    private CommandManager commandManager;
    private ServerManager serverManager;
    private Console console;

    public LoginCommand(String name, String description, CommandManager commandManager, ServerManager serverManager, Console console) {
        super(name, description);
        this.commandManager = commandManager;
        this.serverManager = serverManager;
        this.console = console;
    }

    @Override
    public void execute(String args[]) {
        Profile profile = this.console.profileInput();
        Response response = serverManager.sendCommand(this.getName(), null, null, profile);
        
        if (response != null) {
            response.printMessages();
        
            if (response.getState()) {
                this.commandManager.login();
            }
        }
    }
}
