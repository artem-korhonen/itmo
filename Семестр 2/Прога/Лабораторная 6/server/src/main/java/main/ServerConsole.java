package main;

import java.util.Scanner;

public class ServerConsole implements Runnable {
    private Scanner scanner;
    private CommandManager commandManager;
    private boolean active = true;

    public ServerConsole(CommandManager commandManager) {
        this.scanner = new Scanner(System.in);
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        try {
            String input;
            while (this.active) {
                System.out.print("> ");
                input = this.scanner.nextLine();
                commandManager.executeServerCommand(this, input);
                System.out.println("");
            }
        } finally {
            scanner.close();
        }
    }

    public void breakConsole() {
        this.active = false;
        System.exit(0);
    }
}
