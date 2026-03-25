package commands;

import java.sql.SQLException;
import java.util.List;

import main.DatabaseManager;
import transport.Request;
import transport.Response;
import user.Profile;

public class RegisterCommand extends Command {
    private DatabaseManager databaseManager;

    public RegisterCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public Response execute(Request request) {
        Profile profile = request.getProfile();

        try {
            boolean result = databaseManager.register(profile.getUsername(), profile.getPassword());
            
            if (result) {
                return new Response(List.of("User with username " + profile.getUsername() + " has successfully registered"), result, null);
            } else {
                return new Response(List.of("Error: Username " + profile.getUsername() + " is already taken"), false, null);
            }
        } catch (SQLException e) {
            return new Response(List.of("Error: Failed to register the user"), false, null);
        }
    }
}
