package transport;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;

import data.City;

public class Response implements Serializable {
    private List<String> messages;
    private boolean state;
    private Hashtable<Integer, City> cities;

    public Response(List<String> messages, boolean state, Hashtable<Integer, City> cities) {
        this.messages = messages;
        this.state = state;
        this.cities = cities;
    }

    public String getMessage() {
        String fullMessage = "";
        for (String message : this.messages) {
            fullMessage += message + "\n";
        }
        return fullMessage;
    }

    public void printMessages() {
        for (String message : this.messages) {
            System.out.println(message);
        }
    }

    public boolean getState() {
        return this.state;
    }

    public Hashtable<Integer, City> getCities() {
        return this.cities;
    }
}
