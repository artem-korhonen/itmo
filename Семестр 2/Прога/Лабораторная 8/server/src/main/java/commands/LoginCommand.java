package commands;

import java.sql.SQLException;
import java.util.List;

import main.DatabaseManager;
import transport.Request;
import transport.Response;
import user.Profile;

public class LoginCommand extends Command {
    private DatabaseManager databaseManager;
    
    public LoginCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public Response execute(Request request) {
        Profile profile = request.getProfile();

        try {
            boolean result = databaseManager.login(profile.getUsername(), profile.getPassword());
            
            if (result) {
                return new Response(List.of("Nice"), result, null);
            } else {
                return new Response(List.of("Error: Incorrect username or password"), result, null);
            }
        } catch (SQLException e) {
            return new Response(List.of("Error: User login failed"), false, null);
        }
    }
}
