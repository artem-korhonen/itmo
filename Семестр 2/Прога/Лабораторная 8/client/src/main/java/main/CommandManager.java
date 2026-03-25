package main;

import java.util.List;

import transport.Request;
import transport.Response;

public class CommandManager {
      private ServerManager serverManager;
      private boolean logged;

      public CommandManager(ServerManager serverManager) {
            this.serverManager = serverManager;
            this.logged = true;
      }

      public Response executeCommand(Request request) {
            Response response = serverManager.sendCommand(request);

            if (request.getCommandName().equals("login") || request.getCommandName().equals("register")) {
                  if (response.getState()) {
                        this.login();
                  }
                  return response;
            } else {
                  if (this.isLogged()) {
                        return response;
                  } else {
                        return new Response(List.of("Incorrect Error with logging"), false, null);
                  }
            }
      }

      public void login() {
            this.logged = true;
      }

      public boolean isLogged() {
            return this.logged;
      }
}
